package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserFavRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
sealed interface MessageUIState {
    data object Loading : MessageUIState

    data class Success(
        val message: String,
    ) : MessageUIState

    data class Error(
        val message: String,
    ) : MessageUIState
}

data class FeedUIState(
    val messageState: MessageUIState = MessageUIState.Loading,
    val posts: List<Post> = emptyList(),
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val finalPage: Boolean = false,
)

@HiltViewModel
class FeedViewModel
    @Inject
    constructor(
        private val feedRepository: FeedRepository,
        private val postRepository: PostRepository,
        private val userRepository: UserRepository,
        private val userFavRepository: UserFavRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(FeedUIState())
        val uiState: StateFlow<FeedUIState> = _uiState.asStateFlow()

        private val _navigationEvent = MutableSharedFlow<Int>() // ID del post
        val navigationEvent: SharedFlow<Int> = _navigationEvent

        private val user = MutableStateFlow<UserEntity?>(null)

        private val _userName = MutableStateFlow<String?>(null)

        val userName: StateFlow<String?> = _userName

        private val _usersFavName = MutableStateFlow<List<String>>(emptyList())
        val usersFavName: StateFlow<List<String>> = _usersFavName

        private var getFeedJob: Job? = null
        private var getUserJob: Job? = null
        private var insertJob: Job? = null

        private var page = 1 // Variable para controlar la paginación

        init {
            getUser()
            fetchPosts(reloadScreen = true)
        }

        // User case

        private fun getUser() {
            getUserJob?.cancel()
            getUserJob =
                viewModelScope.launch {
                    user.value = userRepository.getUserFromDataBase()
                    _userName.value = user.value?.name
//                    _userName.value = userRepository.getNameLogged()
                    getUserFavName()
                }
        }

        private fun getUserFavName() {
            getUserJob?.cancel()
            getUserJob =
                viewModelScope.launch {
                    val email = user.value?.email
                    if (email != null) {
                        userFavRepository.getAllNameUserFav(email).collect { result ->
                            _usersFavName.value = result
                        }
                    }
                }
        }

        fun insertUserFav(
            author: String,
            avatarUrl: String,
        ) {
            if (author.isBlank() || avatarUrl.isBlank() || user.value == null) return
            insertJob?.cancel()
            insertJob =
                viewModelScope.launch {
                    val userFav =
                        UserFavEntity(
                            author = author,
                            avatarUrl = avatarUrl,
                            emailLogged = user.value!!.email,
                        )
                    userFavRepository.insertFavUser(userFav, user.value!!.email)
                }
        }

        fun refreshPosts(
            reloadScreen: Boolean = false,
            pullToRefresh: Boolean = false,
        ) {
            fetchPosts(reloadScreen, pullToRefresh)
        }

        // Feed case
        fun loadMorePosts() {
            // Si ya se llegó a la última página, no hacer nada
            if (_uiState.value.finalPage) {
                return
            }
            page++ // Incrementar la página para la próxima llamada
            _uiState.update {
                it.copy(
                    isLoadingMore = true,
                )
            }
            // Llamar a fetchPosts() con reloadScreen = false y pullToRefresh = false
            fetchPosts()
        }

        private fun resetPosts() {
            page = 1 // Reiniciar la página a 1
            _uiState.update {
                it.copy(
                    posts = emptyList(),
                    messageState = MessageUIState.Loading,
                )
            }
        }

        private fun fetchPosts(
            reloadScreen: Boolean = false,
            pullToRefresh: Boolean = false,
        ) {
            getFeedJob?.cancel()
            getFeedJob =
                viewModelScope.launch {
                    if (reloadScreen) {
                        // Usar "reloadScreen = true" mostrara la pantalla de carga
                        resetPosts()
                        _uiState.update {
                            it.copy(
                                messageState = MessageUIState.Loading,
                            )
                        }
                    } else if (pullToRefresh) {
                        // Usar "pullToRefresh = true" activa la animación del pull to refresh
                        resetPosts()
                        _uiState.update {
                            it.copy(isRefreshing = true)
                        }
                    }
                    // Llamar a refreshPosts() para el resto de casos
                    feedRepository
                        .getFeed(
                            page,
                            true,
                        ).collect { result: Resource<List<Post>> ->
                            when (result) {
                                is Resource.Success -> {
                                    // Si llega al final de la página, no se cargan más posts
                                    result.data?.size?.let { size ->
                                        if (size < 20) {
                                            _uiState.update {
                                                it.copy(
                                                    finalPage = true,
                                                )
                                            }
                                        }
                                    }

                                    val newPosts = _uiState.value.posts + (result.data ?: emptyList())
                                    _uiState.update {
                                        it.copy(
                                            isRefreshing = false,
                                            messageState =
                                                MessageUIState.Success(
                                                    "Success",
                                                ),
                                            posts = newPosts,
                                        )
                                    }
                                }

                                is Resource.Error -> {
                                    delay(1000)
                                    Log.e("API call", result.message ?: "Error 400 - Bad Request")
                                    val errorMsg =
                                        "No fue posible conectarse a Internet, revise su conección."
                                    _uiState.update {
                                        it.copy(
                                            isRefreshing = false,
                                            messageState = MessageUIState.Error(errorMsg),
                                        )
                                    }
                                }
                            }
                            _uiState.update {
                                it.copy(
                                    isLoadingMore = false,
                                )
                            }
                        }
                }
        }

        fun isLikePost(
            postId: Int,
            isLiked: Boolean,
        ) {
            likePost(postId, isLiked)
        }

        private fun likePost(
            postId: Int,
            isLiked: Boolean,
        ) {
            viewModelScope.launch {
                postRepository
                    .likePost(
                        postId = postId,
                        liked = isLiked,
                    ).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                fetchPosts()
                            }

                            is Resource.Error ->
                                Log.e("API call", result.message ?: "Error 400 - Bad Request")
                        }
                    }
            }
        }

        override fun onCleared() {
            super.onCleared()
            getFeedJob?.cancel()
        }

        fun goToDetail(postId: Int) {
            viewModelScope.launch {
                _navigationEvent.emit(postId)
            }
        }
    }
