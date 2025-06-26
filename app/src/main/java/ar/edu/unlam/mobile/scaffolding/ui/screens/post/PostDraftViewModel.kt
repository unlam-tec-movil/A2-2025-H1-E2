package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AppDatabase
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.PostEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDraftViewModel
    @Inject
    constructor(
        private val database: AppDatabase,
    ) : ViewModel() {
        private val _drafts = MutableStateFlow<List<PostEntity>>(emptyList())
        val drafts: StateFlow<List<PostEntity>> = _drafts.asStateFlow()

        init {
            getDrafts()
        }

        private fun getDrafts() {
            viewModelScope.launch {
                _drafts.value = database.getPostDao().getAllDrafts()
            }
        }

        fun deleteDraft(id: Long) {
            viewModelScope.launch {
                database.getPostDao().deleteDraftById(id)
                _drafts.value = _drafts.value.filter { it.id != id }
            }
        }
    }
