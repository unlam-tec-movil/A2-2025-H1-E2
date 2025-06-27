package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
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

        val user =
            UserFavEntity(
                author = "John Doe",
                avatarUrl = "https://fotos.perfil.com/2023/06/13/trim/720/410/messi-copa-del-mundo-1588008.jpg?webp",
            )
        val user2 =
            UserFavEntity(
                author = "John Doer",
                avatarUrl = "https://fotos.perfil.com/2023/06/13/trim/720/410/messi-copa-del-mundo-1588008.jpg?webp",
            )
        val user3 =
            UserFavEntity(
                author = "John Doet",
                avatarUrl = "https://fotos.perfil.com/2023/06/13/trim/720/410/messi-copa-del-mundo-1588008.jpg?webp",
            )

        val user4 =
            UserFavEntity(
                author = "John Doess",
                avatarUrl = "https://fotos.perfil.com/2023/06/13/trim/720/410/messi-copa-del-mundo-1588008.jpg?webp",
            )

        val user5 =
            UserFavEntity(
                author = "John DoessR",
                avatarUrl = "https://fotos.perfil.com/2023/06/13/trim/720/410/messi-copa-del-mundo-1588008.jpg?webp",
            )
        val userTest =
            UserFavEntity(
                author = "Deberia estar",
                avatarUrl = "https://fotos.perfil.com/2023/06/13/trim/720/410/messi-copa-del-mundo-1588008.jpg?webp",
            )

        init {
            fetchPosts()
        }

        fun refreshPosts() {
            if (getFeedJob?.isActive == true) {
                return
            }
            fetchPosts(isRefreshingIndicator = true)
        }

        fun insertUserFav() {
            getFeedJob?.cancel()
            getFeedJob =
                viewModelScope.launch {
                    feedRepository.insertFavUser(user)
                    feedRepository.insertFavUser(user2)
                    feedRepository.insertFavUser(user3)
                    feedRepository.insertFavUser(user4)
                    feedRepository.insertFavUser(user5)
                    feedRepository.insertFavUser(userTest)
                }
        }

        private fun fetchPosts(isRefreshingIndicator: Boolean = false) {
            getFeedJob?.cancel()
            getFeedJob =
                viewModelScope.launch {
                    if (isRefreshingIndicator) {
                        _isRefreshing.value = true
                    }
                    feedRepository
                        .getFeed(
                            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImRlbHJpb3NhcmllbEBnbWFpbC5jb20iLCJleHAiOjE3NTI5NTU5MjEsImlzcyI6InVubGFtLXR1aXRlciIsIm5hbWUiOiJkZWxyaW9zYXJpZWxAZ21haWwuY29tIiwic3ViIjoyMTV9.YOg97noxFCqpF-vuXcA6otr27PDm1VBDM4E8zLCb2Q4",
                            // TODO agregar token de usuario, que llegara cuando se logeen
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
