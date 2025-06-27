package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel
@Inject
    constructor(
        private val repository: PostRepository,
    ) : ViewModel() {
        private val _post = MutableStateFlow<Resource<Post>?>(null)
        val post: StateFlow<Resource<Post>?> = _post

        fun getPost(id: Int) {
            viewModelScope.launch {
                repository.getPost(id).collect {
                    _post.value = it
                }
            }
        }
    }
