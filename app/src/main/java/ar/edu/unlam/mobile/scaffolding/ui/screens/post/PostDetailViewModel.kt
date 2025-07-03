package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
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
        private val postRepository: PostRepository,
        private val feedRepository: FeedRepository,
    ) : ViewModel() {
        private val _post = MutableStateFlow<Resource<Post>?>(null)
        val post: StateFlow<Resource<Post>?> = _post
        private val _replies = MutableStateFlow<Resource<List<Post>>>(Resource.Success(emptyList()))
        val replies: StateFlow<Resource<List<Post>>> = _replies

        private var insertJob: Job? = null

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

        fun insertUserFav(
            author: String,
            avatarUrl: String,
        ) {
            if (author.isBlank() || avatarUrl.isBlank()) return
            val userFav = UserFavEntity(author = author, avatarUrl = avatarUrl)
            insertJob?.cancel()
            insertJob =
                viewModelScope.launch {
                    feedRepository.insertFavUser(userFav)
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
