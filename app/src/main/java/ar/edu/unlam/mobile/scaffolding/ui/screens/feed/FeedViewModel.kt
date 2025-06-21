package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import ar.edu.unlam.mobile.scaffolding.data.repositories.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
    @Inject
    constructor(private val feedRepository: FeedRepository,
    ) : ViewModel() {

        val feedState: MutableLiveData<List<Post>> by lazy {
            MutableLiveData<List<Post>>()
        }
        private var getFeedJob: Job? = null

        init {
            getFeedJob?.cancel()
            getFeedJob =
                viewModelScope.launch {
                    feedRepository.getFeed(
                        "", // <-- token de usuario, que llegara cuando se logeen
                        "",// <-- token de la app, aun nose como llegara
                        1,
                        false
                    ).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                feedState.value = result.data
                            }
                            is Resource.Error -> Log.e(
                                "API call",
                                result.message ?: "Error 400 - Bad Request"
                            )
                        }
                    }
                }
        }

    }