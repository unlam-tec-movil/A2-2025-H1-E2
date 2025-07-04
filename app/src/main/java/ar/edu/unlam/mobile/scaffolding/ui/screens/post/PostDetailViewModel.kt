package ar.edu.unlam.mobile.scaffolding.ui.screens.post

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel
    @Inject
    constructor(
        private val repository: PostRepository,
        private val feedRepository: FeedRepository,
        private val userRepository: UserRepository,
        private val userFavRepository: UserFavRepository,
    ) : ViewModel() {
        private val _post = MutableStateFlow<Resource<Post>?>(null)
        val post: StateFlow<Resource<Post>?> = _post
        private val _replies = MutableStateFlow<Resource<List<Post>>>(Resource.Success(emptyList()))
        val replies: StateFlow<Resource<List<Post>>> = _replies

        private var insertJob: Job? = null
        private var getUserJob: Job? = null
        private val user = MutableStateFlow<UserEntity?>(null)

        fun getUserName(): String = user.value?.name ?: ""

        init {
            getUser()
        }

        private fun getUser() {
            getUserJob?.cancel()
            getUserJob =
                viewModelScope.launch {
                    user.value = userRepository.getUserFromDataBase()
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
                            userOwnerEmail =
                                user.value!!
                                    .email,
                        )
                    userFavRepository.insertFavUser(userFav, user.value!!.email)
                }
        }

        fun getPost(id: Int) {
            viewModelScope.launch {
                repository.getPost(id).collect {
                    _post.value = it
                }
            }
        }

        fun getPostReplies(postId: Int) {
            viewModelScope.launch {
                repository.getPostReplies(postId).collect {
                    _replies.value = it
                }
            }
        }

        fun sendReply(
            postId: Int,
            message: String,
        ) {
            viewModelScope.launch {
                repository.sendReply(postId, message).collect { result ->
                    if (result is Resource.Success) {
                        getPostReplies(postId)
                    }
                }
            }
        }
    }
