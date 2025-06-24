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
import kotlin.math.log

@HiltViewModel
class PostCreateViewModel
    @Inject
    constructor(
        private val postRepository: PostRepository
    ) : ViewModel() {

        var myMessage by mutableStateOf("")
            private set
        fun onDescriptionChange(newDescription: String) {
            myMessage = newDescription
        }

        private val _statusMessage = MutableStateFlow<String?>(null)
        val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

        private var newPostJob: Job? = null

        fun createPost(message: String) {
            newPostJob?.cancel()
            if (myMessage.isBlank()) {
                _statusMessage.value = "Error: El mensaje no puede estar vacío."
                Log.e("API call", "Error: El mensaje no puede estar vacío.")
                return
            }
            newPostJob =
                viewModelScope.launch {
                    Log.i("API call", "Datos para crear post obtenidos")
                    postRepository.createPosts(
                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImxhcmFtYXlvNzUyQGFsdW1uby51bmxhbS5lZHUuYXIiLCJleHAiOjE3NTMzOTAyMjMsImlzcyI6InVubGFtLXR1aXRlciIsIm5hbWUiOiJsYXJhbWF5bzc1MkBhbHVtbm8udW5sYW0uZWR1LmFyIiwic3ViIjoyMTJ9.cy_gfyxNIgGLp9MwtzTrCKGdU019UvleOgtCS5rTJd0",
                        "77f54753a40f7cf44a0b4d9e69a65c24dff64022329bb75afc1196b43187399a",
                        message
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
                                Log.e(
                                    "API call", result.message ?: "Error 400 - Bad Request",
                                )
                            }
                        }
                    }
                }
        }
    }
