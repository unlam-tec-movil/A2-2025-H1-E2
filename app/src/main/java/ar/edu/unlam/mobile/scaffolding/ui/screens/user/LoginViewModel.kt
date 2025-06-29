package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        init {
            // Chequear si ya hay un usuario guardado en la base de datos local
        }

        private val _showToastEvent = MutableSharedFlow<String>()
        val showToastEvent = _showToastEvent.asSharedFlow()

        private var loginUserJob: Job? = null

        fun loginUser(
            email: String,
            password: String,
            navController: NavController,
        ) {
            viewModelScope.launch {
                loginUserJob?.cancel()
                loginUserJob =
                    CoroutineScope(Dispatchers.Main).launch {
                        userRepository.loginUser(email, password).collect { result ->
                            when (result) {
                                is Resource.Success -> {
                                    navController.navigate("feed")
                                }
                                is Resource.Error -> {
                                    val message = result.message ?: "Error 400 - Bad Request"
                                    Log.e("API call", message)
                                    showToast(message)
                                }
                            }
                        }
                    }
            }
        }

        fun showToast(message: String) {
            viewModelScope.launch {
                _showToastEvent.emit(message)
            }
        }
    }
