package ar.edu.unlam.mobile.scaffolding.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepositoryTest
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserFavRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenTestViewModel
    @Inject
    constructor(
        feedRepository: FeedRepositoryTest,
        private val userRepository: UserRepository,
        private val userFavRepository: UserFavRepository,
        private val postRepository: PostRepository,
    ) : ViewModel() {
        val post: Flow<PagingData<Post>> = feedRepository.getFeed()
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
        }

        // User case

        private fun getUser() {
            getUserJob?.cancel()
            getUserJob =
                viewModelScope.launch {
                    val userExist = userRepository.getUserFromDataBase()
                    if (userExist != null) {
                        user.value = userExist
                        _userName.value = user.value?.name
                        getUserFavName()
                    } else {
                        _usersFavName.value = emptyList()
                    }
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
                    userFavRepository.followUser(userFav, user.value!!.email)
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
                            }

                            is Resource.Error ->
                                Log.e("API call", result.message ?: "Error 400 - Bad Request")
                        }
                    }
            }
        }

        fun goToDetail(postId: Int) {
            viewModelScope.launch {
                _navigationEvent.emit(postId)
            }
        }
    }
