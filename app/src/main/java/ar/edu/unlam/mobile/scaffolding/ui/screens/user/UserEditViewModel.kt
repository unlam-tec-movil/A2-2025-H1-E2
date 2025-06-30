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
class UserEditViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _currentUserState = mutableStateOf(UserProfileModel())
        val currentUserState: State<UserProfileModel> = _currentUserState

        private var editUserJob: Job? = null
        private var getCurrentUserJob: Job? = null

        private var _name = MutableStateFlow("")
        var name: StateFlow<String> = _name

        private var _password = MutableStateFlow("")
        var password: StateFlow<String> = _password

        private var _confirmPassword = MutableStateFlow("")
        var confirmPassword: StateFlow<String> = _confirmPassword

        // MESSAGE
        private var _nameError = MutableStateFlow<String?>(null)
        var nameError: StateFlow<String?> = _nameError

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
            password: String,
            confirmPassWord: String,
        ): Boolean {
            _nameError.value = FormValidator.isValidText(text = name)
            _passwordError.value = FormValidator.isValidText(text = password, specialCharacters = true)
            _confirmPasswordError.value = FormValidator.isValidPassword(password, confirmPassWord)

            return _nameError.value == null &&
                _passwordError.value == null &&
                _confirmPasswordError.value == null
        }

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

                            is Resource.Error ->
                                Log.e(
                                    "API call",
                                    result.message ?: "Error 400 - Bad Request",
                                )
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
                        userRepository
                            .editUser(name, _currentUserState.value.avatarURL, password)
                            .collect { result ->
                                when (result) {
                                    is Resource.Success -> {
                                        navController.navigate("user")
                                    }

                                    is Resource.Error -> {
                                        Log.e(
                                            "API call",
                                            result.message ?: "Ocurrió un error inesperado",
                                        )
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
