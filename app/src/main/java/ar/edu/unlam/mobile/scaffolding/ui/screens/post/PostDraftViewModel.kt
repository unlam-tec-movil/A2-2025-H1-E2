package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PostDraftViewModel : ViewModel() {
    //TODO: obtener los drafts con Room
    var drafts = mutableStateListOf<String>()
        private set

    fun deleteDraft(index: Int) {
        if (index in drafts.indices) drafts.removeAt(index)
    }
}
