package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
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
)

@HiltViewModel
class FeedViewModel
    @Inject
    constructor(
        private val feedRepository: FeedRepository,
        private val postRepository: PostRepository,
    ) : ViewModel() {
        private val _isRefreshing = MutableStateFlow(false)
        val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()
        private val _uiState = MutableStateFlow(FeedUIState())
        val uiState: StateFlow<FeedUIState> = _uiState.asStateFlow()

        private var getFeedJob: Job? = null

        init {
            fetchPosts(isInitialLoad = true)
        }

        fun refreshPosts(refreshing: Boolean = true) {
            if (getFeedJob?.isActive == true) {
                return
            }
            fetchPosts(pullToRefresh = refreshing)
        }

        private fun fetchPosts(
            isInitialLoad: Boolean = false,
            pullToRefresh: Boolean = false,
        ) {
            getFeedJob?.cancel()
            getFeedJob =
                viewModelScope.launch {
                    if (isInitialLoad) {
                        _uiState.update {
                            it.copy(
                                messageState = MessageUIState.Loading,
                            )
                        }
                    } else if (pullToRefresh) {
                        _isRefreshing.value = true
                    }
                    feedRepository.getFeed(
                        1,
                        false,
                    ).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                _uiState.update {
                                    it.copy(
                                        messageState =
                                            MessageUIState.Success(
                                                message = "Success",
                                                posts = result.data!!,
                                            ),
                                    )
                                }
                            }
                            is Resource.Error ->
                                _uiState.update {
                                    it.copy(
                                        messageState =
                                            MessageUIState.Error(
                                                message = result.message ?: "Error 400 - Bad Request",
                                            ),
                                    )
                                }
                        }
                    }
                    if (pullToRefresh) {
                        _isRefreshing.value = false
                        Log.d("FeedViewModel", "Refreshing finished")
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
    }
