package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AppDatabase
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.PostEntity
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
        private val database: AppDatabase,
    ) : ViewModel() {
        var myMessage by mutableStateOf("")
            private set

        fun onDescriptionChange(newDescription: String) {
            myMessage = newDescription
        }

        private val _statusMessage = MutableStateFlow<String?>(null)
        val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

        private val _isLoading = MutableStateFlow<Boolean>(false)
        val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

        @Suppress("ktlint:standard:backing-property-naming")
        private var _draftId: Long? = null
        private var newPostJob: Job? = null

        fun setInitialContent(draftId: Long) {
            viewModelScope.launch {
                _draftId = draftId
                val post = database.getPostDao().getById(draftId)
                myMessage = post.content
            }
        }

        fun createPost() {
            if (myMessage.isEmpty() || _isLoading.value) {
                return
            }
            _isLoading.value = true
            newPostJob?.cancel()
            if (myMessage.isBlank()) {
                _statusMessage.value = "Error: El mensaje no puede estar vacío."
                Log.e("API call", "Error: El mensaje no puede estar vacío.")
                return
            }
            newPostJob =
                viewModelScope.launch {
                    postRepository
                        .createPosts(
                            myMessage,
                        ).collect { result ->
                            _isLoading.value = false
                            when (result) {
                                is Resource.Success -> {
                                    _statusMessage.value = result.data!!
                                    Log.d("API call", result.data)

                                    _draftId?.let { deleteDraft(it) }
                                }

                                is Resource.Error -> {
                                    _statusMessage.value = result.message!!
                                    Log.e("API call", result.message)
                                }
                            }
                        }
                }
        }

        fun deleteDraft(id: Long) {
            viewModelScope.launch {
                database.getPostDao().deleteDraftById(id)
                myMessage = ""
            }
        }

        fun saveDraft(myMessage: String) {
            viewModelScope.launch {
                val post = PostEntity(content = myMessage, isDraft = true)
                database.getPostDao().insertPost(post)
            }
        }
    }
