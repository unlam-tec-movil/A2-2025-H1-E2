package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
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
        val posts: List<Post>,
    ) : MessageUIState

    data class Error(
        val message: String,
    ) : MessageUIState
}

data class FeedUIState(
    val messageState: MessageUIState = MessageUIState.Loading,
    val posts: List<Post> = emptyList(),
    val isRefreshing: Boolean = false,
)

@HiltViewModel
class FeedViewModel
    @Inject
    constructor(
        private val feedRepository: FeedRepository,
        private val postRepository: PostRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(FeedUIState())
        val uiState: StateFlow<FeedUIState> = _uiState.asStateFlow()

        private val _navigationEvent = MutableSharedFlow<Int>() // ID del post
        val navigationEvent: SharedFlow<Int> = _navigationEvent

        private var getFeedJob: Job? = null

        init {
            fetchPosts(reloadScreen = true)
        }

        fun refreshPosts(
            reloadScreen: Boolean = false,
            pullToRefresh: Boolean = false,
        ) {
            fetchPosts(reloadScreen, pullToRefresh)
        }

        fun insertUserFav(
            author: String,
            avatarUrl: String,
        ) {
            if (author.isBlank() || avatarUrl.isBlank()) return
            val userFav = UserFavEntity(author = author, avatarUrl = avatarUrl)
            getFeedJob?.cancel()
            getFeedJob =
                viewModelScope.launch {
                    feedRepository.insertFavUser(userFav)
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
                        _uiState.update {
                            it.copy(
                                messageState = MessageUIState.Loading,
                            )
                        }
                    } else if (pullToRefresh) {
                        // Usar "pullToRefresh = true" activa la animación del pull to refresh
                        _uiState.update {
                            it.copy(isRefreshing = true)
                        }
                    }
                    // Llamar a refreshPosts() para el resto de casos
                    feedRepository.getFeed(
                        1,
                        false,
                    ).collect {
                            result: Resource<List<Post>> ->
                        when (result) {
                            is Resource.Success -> {
                                _uiState.update {
                                    it.copy(
                                        isRefreshing = false,
                                        messageState =
                                            MessageUIState.Success(
                                                "Success",
                                                result.data!!,
                                            ),
                                    )
                                }
                            }
                            is Resource.Error -> {
                                delay(1000)
                                Log.e("API call", result.message ?: "Error 400 - Bad Request")
                                val errorMsg = "No fue posible conectarse a Internet, revise su conección."
                                _uiState.update {
                                    it.copy(
                                        isRefreshing = false,
                                        messageState = MessageUIState.Error(errorMsg),
                                    )
                                }
                            }
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
                postRepository.likePost(
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
