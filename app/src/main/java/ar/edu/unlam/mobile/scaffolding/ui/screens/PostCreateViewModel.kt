package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostCreateViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository,
    ) : ViewModel() {
        var myMessage by mutableStateOf("")
            private set

        fun onDescriptionChange(newDescription: String) {
            myMessage = newDescription
        }

        private val _statusMessage = MutableStateFlow<String?>(null)
        val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

        private var newPostJob: Job? = null

        fun createPost() {
            newPostJob?.cancel()
            if (myMessage.isBlank()) {
                _statusMessage.value = "Error: El mensaje no puede estar vacío."
                Log.e("API call", "Error: El mensaje no puede estar vacío.")
                return
            }
            newPostJob =
                viewModelScope.launch {
                    postRepository.createPosts(
                        "",
                        // TODO: Obtener el token del usuario logeado
                        myMessage,
                    ).collect {
                            result ->
                        when (result) {
                            is Resource.Success -> {
                                _statusMessage.value = result.data!!
                                Log.d("API call", result.data)
                                myMessage = ""
                            }
                            is Resource.Error -> {
                                _statusMessage.value = result.message!!
                                Log.e("API call", result.message)
                            }
                        }
                    }
                }
        }
    }
