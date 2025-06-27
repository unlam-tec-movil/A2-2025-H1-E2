package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
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
        private val _userFavState = MutableStateFlow<List<UserFavEntity>>(emptyList())
        val userFavState: StateFlow<List<UserFavEntity>> = _userFavState.asStateFlow()

        private var userFavJob: Job? = null

        init {
            getUsers()
        }

        private fun getUsers() {
            userFavJob?.cancel()
            userFavJob =
                viewModelScope.launch {
                    repository.getFavUser().collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                _userFavState.value = result.data!!
                            }

                            is Resource.Error ->
                                Log.e("Data Base call", result.message ?: "Error")
                        }
                    }
                }
        }
    }
