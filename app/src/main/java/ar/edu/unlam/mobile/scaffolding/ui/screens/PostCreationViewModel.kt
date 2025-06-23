package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostCreationViewModel
    @Inject
    constructor(
        // private val postRepository: PostRepository
    ) : ViewModel() {
        var description by mutableStateOf("")
            private set

        fun onDescriptionChange(newDescription: String) {
            description = newDescription
        }

        fun createTuit() {
            // TODO Lógica para crear el tuit usando descripcion
            print("Tuit creado: $description")
        }
    }
