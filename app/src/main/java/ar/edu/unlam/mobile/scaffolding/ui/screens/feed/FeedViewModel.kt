package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepository
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
    @Inject
    constructor(
        private val feedRepository: FeedRepository,
    ) : ViewModel() {
        private val _feedState = MutableStateFlow<List<Post>>(emptyList())
        val feedState: StateFlow<List<Post>> = _feedState.asStateFlow()

        private val _isRefreshing = MutableStateFlow(false)
        val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

        private var getFeedJob: Job? = null

        init {
            fetchPosts()
        }

        fun refreshPosts() {
            if (getFeedJob?.isActive == true) {
                return
            }
            fetchPosts(isRefreshingIndicator = true)
        }

        private fun fetchPosts(isRefreshingIndicator: Boolean = false) {
            getFeedJob?.cancel()
            getFeedJob =
                viewModelScope.launch {
                    if (isRefreshingIndicator) {
                        _isRefreshing.value = true
                    }
                    feedRepository.getFeed(
                        1,
                        false,
                    ).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                _feedState.value = result.data!!
                            }
                            is Resource.Error ->
                                Log.e(
                                    "API call",
                                    result.message ?: "Error 400 - Bad Request",
                                )
                        }
                    }
                    if (isRefreshingIndicator) {
                        _isRefreshing.value = false
                    }
                }
        }

        override fun onCleared() {
            super.onCleared()
            getFeedJob?.cancel()
        }
    }
