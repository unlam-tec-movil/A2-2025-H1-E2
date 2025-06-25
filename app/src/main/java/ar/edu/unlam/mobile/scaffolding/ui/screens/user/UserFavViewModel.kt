package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.model.UserFavModel
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserFavViewModel
    @Inject
    constructor(
        private val repository: UserRepository,
    ) : ViewModel() {
        private val _userFavState = MutableStateFlow<List<UserFavModel>>(emptyList())
        val userFavState: StateFlow<List<UserFavModel>> = _userFavState.asStateFlow()

        private var userFavJob: Job? = null

        private fun getUsers() {
            userFavJob?.cancel()

            userFavJob =
                viewModelScope.launch {
                    repository.getFavUser()
                }
        }
    }
