package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserFavRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val userFavRepository: UserFavRepository,
        private val postRepository: PostRepository,

    ) : ViewModel() {
        private val _post = MutableStateFlow<Resource<Post>?>(null)
        val post: StateFlow<Resource<Post>?> = _post
        private val _replies = MutableStateFlow<Resource<List<Post>>>(Resource.Success(emptyList()))
        val replies: StateFlow<Resource<List<Post>>> = _replies

        private val user = MutableStateFlow<UserEntity?>(null)

        private val _userName = MutableStateFlow<String?>(null)

        val userName: StateFlow<String?> = _userName

        private val _usersFavName = MutableStateFlow<List<String>>(emptyList())
        val usersFavName: StateFlow<List<String>> = _usersFavName

        private var getUserJob: Job? = null
        private var insertJob: Job? = null

        init {
            getUser()
        }

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


        fun getPost(id: Int) {
            viewModelScope.launch {
                postRepository.getPost(id).collect {
                    _post.value = it
                }
            }
        }

        fun getPostReplies(postId: Int) {
            viewModelScope.launch {
                postRepository.getPostReplies(postId).collect {
                    _replies.value = it
                }
            }
        }

        fun sendReply(
            postId: Int,
            message: String,
        ) {
            viewModelScope.launch {
                postRepository.sendReply(postId, message).collect { result ->
                    if (result is Resource.Success) {
                        getPostReplies(postId)
                    }
                }
            }
        }

        fun isLikePost(
            postLikeId: Int,
            isLiked: Boolean,
            mainPost: Int? = null,
        ) {
            likePost(postLikeId, isLiked, mainPost)
        }

        private fun likePost(
            postLikeId: Int,
            isLiked: Boolean,
            mainPost: Int? = null,
        ) {
            viewModelScope.launch {
                postRepository.likePost(
                    postId = postLikeId,
                    liked = isLiked,
                ).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            if (mainPost != null) {
                                getPostReplies(mainPost)
                            } else {
                                getPost(postLikeId)
                            }
                        }
                        is Resource.Error ->
                            Log.e("API call", result.message ?: "Error 400 - Bad Request")
                    }
                }
            }
        }
    }
