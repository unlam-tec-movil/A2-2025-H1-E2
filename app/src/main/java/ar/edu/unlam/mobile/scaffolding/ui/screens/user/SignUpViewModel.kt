package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.domain.Authentication
import ar.edu.unlam.mobile.scaffolding.domain.FormValidator
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
    ) : ViewModel() {
        private var userJob: Job? = null

        private var _name = MutableStateFlow("")
        var name: StateFlow<String> = _name

        private var _email = MutableStateFlow("")
        var email: StateFlow<String> = _email

        private var _password = MutableStateFlow("")
        var password: StateFlow<String> = _password

        private var _confirmPassword = MutableStateFlow("")
        var confirmPassword: StateFlow<String> = _confirmPassword

        // MESSAGE
        private var _nameError = MutableStateFlow<String?>(null)
        var nameError: StateFlow<String?> = _nameError

        private var _emailError = MutableStateFlow<String?>(null)
        var emailError: StateFlow<String?> = _emailError

        private var _passwordError = MutableStateFlow<String?>(null)
        var passwordError: StateFlow<String?> = _passwordError

        private var _confirmPasswordError = MutableStateFlow<String?>(null)
        var confirmPasswordError: StateFlow<String?> = _confirmPasswordError

        private var _message = MutableStateFlow<String?>(null)
        var message: StateFlow<String?> = _message

        // Onchange
        fun onNameChange(name: String) {
            _nameError.value = null
            _name.value = name
        }

        fun onEmailChange(email: String) {
            _emailError.value = null
            _email.value = email
        }

        fun onPassWordChange(password: String) {
            _passwordError.value = null
            _password.value = password
        }

        fun onConfirmPassWordChange(confirmPassWord: String) {
            _confirmPasswordError.value = null
            _confirmPassword.value = confirmPassWord
        }

        // onFocusLost

        fun onNameFocusLost(name: String) {
            if (name.isNotBlank()) {
                _nameError.value = FormValidator.isValidText(name)
            }
        }

        fun onEmailFocusLost(email: String) {
            if (email.isNotBlank()) {
                _emailError.value = FormValidator.isValidEmail(email)
            }
        }

        fun onPassWordFocusLost(password: String) {
            if (password.isNotBlank()) {
                _passwordError.value = FormValidator.isValidText(password, specialCharacters = true)
            }
        }

        fun onConfirmPassWordFocusLost(
            password: String,
            confirmPassWord: String,
        ) {
            if (confirmPassWord.isNotBlank()) {
                _confirmPasswordError.value = FormValidator.isValidPassword(password, confirmPassWord)
            }
        }

        fun validateDate(
            name: String,
            email: String,
            password: String,
            confirmPassWord: String,
        ): Boolean {
            _nameError.value = FormValidator.isValidText(text = name)
            _emailError.value = FormValidator.isValidEmail(email = email)
            _passwordError.value = FormValidator.isValidText(text = password, specialCharacters = true)
            _confirmPasswordError.value = FormValidator.isValidPassword(password, confirmPassWord)

            return _nameError.value == null &&
                _emailError.value == null &&
                _passwordError.value == null &&
                _confirmPasswordError.value == null
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
                                    _message.value =
                                        Authentication.messageApi(result.message, isSingUpOrigin = true)
                                    navController.navigate("feed") {
                                        popUpTo("login") {
                                            inclusive = true
                                        }
                                    }
                                }

                                is Resource.Error -> {
                                    _message.value = Authentication.messageApi(result.message)
                                    Log.e("API call", result.message ?: "Error 400 - Bad Request")
                                }
                            }
                        }
                    }
            }
        }

        fun clearMessage() {
            _message.value = null
        }
    }
