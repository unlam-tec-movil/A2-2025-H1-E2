package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.model.UserProfileModel
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _currentUserState = mutableStateOf(UserProfileModel())
        val currentUserState: State<UserProfileModel> = _currentUserState
        private var getCurrentUserJob: Job? = null
        private var logoutJob: Job? = null

        init {
            getCurrentUserJob?.cancel()
            getCurrentUserJob =
                viewModelScope.launch {
                    userRepository.getCurrentUser().collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                _currentUserState.value =
                                    currentUserState.value.copy(
                                        name = result.data?.name ?: "",
                                        email = result.data?.email ?: "",
                                        avatarURL = result.data?.avatarURL ?: "",
                                    )
                            }
                            is Resource.Error -> Log.e("API call", result.message ?: "Ocurrió un error inesperado")
                        }
                    }
                }
        }

        fun logout(navController: NavController) {
            logoutJob?.cancel()
            logoutJob =
                viewModelScope.launch {
                    userRepository.logoutUser().collect { result ->
                        when (result) {
                            is Resource.Success -> navController.navigate("login")
                            is Resource.Error -> Log.e("DB call", result.message ?: "Ocurrió un error inesperado")
                        }
                    }
                }
        }
    }
