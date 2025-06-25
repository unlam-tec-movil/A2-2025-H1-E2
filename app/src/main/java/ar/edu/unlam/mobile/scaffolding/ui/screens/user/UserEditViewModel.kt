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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserEditViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _currentUserState = mutableStateOf(UserProfileModel())
        val currentUserState: State<UserProfileModel> = _currentUserState

        private var editUserJob: Job? = null
        private var getCurrentUserJob: Job? = null

        init {
            // Obtener los datos del usuario para cargar los campos con la información
            getCurrentUserJob?.cancel()
            getCurrentUserJob =
                viewModelScope.launch {
                    userRepository.getCurrentUser().collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                _currentUserState.value =
                                    currentUserState.value.copy(
                                        name = result.data?.name ?: "",
                                        email = "",
                                        avatarURL = result.data?.avatarURL ?: "",
                                    )
                            }
                            is Resource.Error -> Log.e("API call", result.message ?: "Error 400 - Bad Request")
                        }
                    }
                }
        }

        fun editUser(
            name: String,
            password: String,
            navController: NavController,
        ) {
            viewModelScope.launch {
                editUserJob?.cancel()
                editUserJob =
                    CoroutineScope(Dispatchers.Main).launch {
                        userRepository.editUser(name, _currentUserState.value.avatarURL, password).collect { result ->
                            when (result) {
                                is Resource.Success -> {
                                    navController.navigate("user")
                                }
                                is Resource.Error -> {
                                    Log.e("API call", result.message ?: "Ocurrió un error inesperado")
                                }
                            }
                        }
                    }
            }
        }
    }
