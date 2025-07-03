package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
        private var error: String? = null

        private var _state = MutableStateFlow(FormState())
        var state: StateFlow<FormState> = _state

        private val _validation = mutableStateOf(false)
        val validation = _validation

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
                error = validateName.validateName(name).errorMessage
                _state.value = _state.value.copy(nameError = error)
            }
        }

        fun onEmailFocusLost(email: String) {
            if (email.isNotBlank()) {
                error = validateEmail.validateEmail(email).errorMessage
                _state.value = _state.value.copy(emailError = error)
            }
        }

        fun onPasswordFocusLost(password: String) {
            if (password.isNotBlank()) {
                error = validatePassword.validatePassword(password).errorMessage
                _state.value = _state.value.copy(passwordError = error)
            }
        }

        fun onConfirmPasswordFocusLost(confirmPassword: String) {
            if (confirmPassword.isNotBlank()) {
                error =
                    validateConfirmPassword
                        .validateConfirmPassword(
                            _state.value.password,
                            confirmPassword,
                        ).errorMessage
                _state.value = _state.value.copy(confirmPasswordError = error)
            }
        }

        fun submitData() {
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
                return
            } else {
                _validation.value = true
                return
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
