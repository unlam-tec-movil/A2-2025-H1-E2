package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.domain.authentication.FormState
import ar.edu.unlam.mobile.scaffolding.domain.authentication.ValidateEmail
import ar.edu.unlam.mobile.scaffolding.domain.authentication.ValidatePassword
import ar.edu.unlam.mobile.scaffolding.ui.util.MessageFromApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val validateEmail: ValidateEmail,
        private val validatePassword: ValidatePassword,
        private val messageFromApi: MessageFromApi,
    ) : ViewModel() {
        private var loginUserJob: Job? = null

        private val _state = MutableStateFlow(FormState())
        val state: StateFlow<FormState> = _state

        private val _messageDialog = mutableStateOf<String?>(null)
        val messageDialog = _messageDialog

        // onChanged
        fun onEmailChanged(email: String) {
            _state.value = _state.value.copy(email = email, emailError = null)
        }

        fun onPasswordChanged(password: String) {
            _state.value = _state.value.copy(password = password, passwordError = null)
        }

        // onFocusLost
        fun onEmailFocusLost(email: String) {
            if (email.isNotBlank()) {
                _state.value =
                    _state.value.copy(emailError = validateEmail.validateEmail(email).errorMessage)
            }
        }

        fun onPasswordFocusLost(password: String) {
            if (password.isNotBlank()) {
                _state.value =
                    _state.value.copy(passwordError = validatePassword.validatePassword(password).errorMessage)
            }
        }

        fun submitData(): Boolean {
            val emailResult = validateEmail.validateEmail(_state.value.email)
            val passwordResult = validatePassword.validatePassword(_state.value.password)

            val isError =
                listOf(
                    emailResult,
                    passwordResult,
                ).any { !it.successful }

            if (isError) {
                _state.value =
                    _state.value.copy(
                        emailError = emailResult.errorMessage,
                        passwordError = passwordResult.errorMessage,
                    )
                return false
            } else {
                return true
            }
        }

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
                                    navController.navigate("feed") {
                                        popUpTo("login") {
                                            inclusive = true
                                        }
                                    }
                                }

                                is Resource.Error -> {
                                    result.message?.let { errorMessage(it) }
                                    Log.e("API call", result.message ?: "Error 400 - Bad Request")
                                }
                            }
                        }
                    }
            }
        }

        fun dismissRequest() {
            _messageDialog.value = null
        }

        private fun errorMessage(message: String) {
            _messageDialog.value = messageFromApi.errorMessage(message)
        }
    }
