package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.domain.authentication.FormState
import ar.edu.unlam.mobile.scaffolding.domain.authentication.ValidateConfirmPassword
import ar.edu.unlam.mobile.scaffolding.domain.authentication.ValidateEmail
import ar.edu.unlam.mobile.scaffolding.domain.authentication.ValidateName
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
class SignUpViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val validateName: ValidateName,
        private val validateEmail: ValidateEmail,
        private val validatePassword: ValidatePassword,
        private val validateConfirmPassword: ValidateConfirmPassword,
        private val messageFromApi: MessageFromApi,
    ) : ViewModel() {
        private var userJob: Job? = null

        private var _state = MutableStateFlow(FormState())
        var state: StateFlow<FormState> = _state

        private val _showErrorDialog = mutableStateOf<String?>(null)
        val showErrorDialog = _showErrorDialog

        // onChanged
        fun onNameChanged(name: String) {
            _state.value = _state.value.copy(name = name, nameError = null)
        }

        fun onEmailChanged(email: String) {
            _state.value = _state.value.copy(email = email, emailError = null)
        }

        fun onPassWordChanged(password: String) {
            _state.value = _state.value.copy(password = password, passwordError = null)
        }

        fun onConfirmPasswordChanged(confirmPassword: String) {
            _state.value =
                _state.value.copy(confirmPassword = confirmPassword, confirmPasswordError = null)
        }

        // onFocusLost
        fun onNameFocusLost(name: String) {
            if (name.isNotBlank()) {
                _state.value =
                    _state.value.copy(nameError = validateName.validateName(name).errorMessage)
            }
        }

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

        fun onConfirmPasswordFocusLost(confirmPassword: String) {
            if (confirmPassword.isNotBlank()) {
                _state.value =
                    _state.value.copy(
                        confirmPasswordError =
                            validateConfirmPassword
                                .validateConfirmPassword(
                                    _state.value.password,
                                    confirmPassword,
                                ).errorMessage,
                    )
            }
        }

        fun submitData(): Boolean {
            val nameResult = validateName.validateName(_state.value.name)
            val emailResult = validateEmail.validateEmail(_state.value.email)
            val passwordResult = validatePassword.validatePassword(_state.value.password)
            val confirmPassword =
                validateConfirmPassword.validateConfirmPassword(
                    _state.value.password,
                    _state.value.confirmPassword,
                )

            val isError =
                listOf(
                    nameResult,
                    emailResult,
                    passwordResult,
                    confirmPassword,
                ).any { !it.successful }

            if (isError) {
                _state.value =
                    _state.value.copy(
                        nameError = nameResult.errorMessage,
                        emailError = emailResult.errorMessage,
                        passwordError = passwordResult.errorMessage,
                        confirmPasswordError = confirmPassword.errorMessage,
                    )
                return false
            } else {
                return true
            }
        }

        fun signUpUser(
            name: String,
            email: String,
            password: String,
            navController: NavController,
        ) {
            viewModelScope.launch {
                userJob?.cancel()
                userJob =
                    CoroutineScope(Dispatchers.Main).launch {
                        userRepository.signUpUser(name, email, password).collect { result ->
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
            _showErrorDialog.value = null
        }

        private fun errorMessage(message: String) {
            _showErrorDialog.value = messageFromApi.errorMessage(message)
        }
    }
