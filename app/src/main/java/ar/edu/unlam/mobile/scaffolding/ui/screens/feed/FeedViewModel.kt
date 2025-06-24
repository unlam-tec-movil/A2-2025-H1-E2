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
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImxhcmFtYXlvNzUyQGFsdW1uby51bmxhbS5lZHUuYXIiLCJleHAiOjE3NTMzOTAyMjMsImlzcyI6InVubGFtLXR1aXRlciIsIm5hbWUiOiJsYXJhbWF5bzc1MkBhbHVtbm8udW5sYW0uZWR1LmFyIiwic3ViIjoyMTJ9.cy_gfyxNIgGLp9MwtzTrCKGdU019UvleOgtCS5rTJd0",
                        // TODO agregar token de usuario, que llegara cuando se logeen
                        "77f54753a40f7cf44a0b4d9e69a65c24dff64022329bb75afc1196b43187399a",
                        // TODO agregar token de la app, aun nose como llegara
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
