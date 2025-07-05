package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private var loginUserJob: Job? = null

        private val _isUserLogged = MutableStateFlow(false)
        val isUserLogged: StateFlow<Boolean> = _isUserLogged

        private val _isLoading = mutableStateOf(true)
        val isLoading = _isLoading

        init {
            isLogged()
        }

        private fun isLogged() {
            loginUserJob?.cancel()
            loginUserJob =

                viewModelScope.launch {
                    _isUserLogged.value = userRepository.isUserLogged()
                    _isLoading.value = false
                }
        }
    }
