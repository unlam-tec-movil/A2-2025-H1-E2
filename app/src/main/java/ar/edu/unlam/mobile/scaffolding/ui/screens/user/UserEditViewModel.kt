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
import ar.edu.unlam.mobile.scaffolding.domain.authentication.FormState
import ar.edu.unlam.mobile.scaffolding.domain.authentication.ValidateConfirmPassword
import ar.edu.unlam.mobile.scaffolding.domain.authentication.ValidateName
import ar.edu.unlam.mobile.scaffolding.domain.authentication.ValidatePassword
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
        private val validateName: ValidateName,
        private val validatePassword: ValidatePassword,
        private val validateConfirmPassword: ValidateConfirmPassword,
    ) : ViewModel() {
        private val _currentUserState = mutableStateOf(UserProfileModel())
        val currentUserState: State<UserProfileModel> = _currentUserState

        private var editUserJob: Job? = null
        private var getCurrentUserJob: Job? = null

        private val _state = MutableStateFlow(FormState())
        val state: StateFlow<FormState> = _state

        // OnChanged
        fun onNameChanged(name: String) {
            _state.value = _state.value.copy(name = name, nameError = null)
        }

        fun onPasswordChanged(password: String) {
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

        fun onPasswordFocusLost(password: String) {
            if (password.isNotBlank()) {
                _state.value =
                    _state.value.copy(passwordError = validatePassword.validatePassword(password).errorMessage)
            }
        }

        fun onConfirmPasswordFocusLost(confirmPassWord: String) {
            if (confirmPassWord.isNotBlank()) {
                val error =
                    validateConfirmPassword
                        .validateConfirmPassword(
                            _state.value.password,
                            confirmPassWord,
                        ).errorMessage
                _state.value = _state.value.copy(confirmPasswordError = error)
            }
        }

        fun submitData(): Boolean {
            val nameResult = validateName.validateName(_state.value.name)
            val passwordResult = validatePassword.validatePassword(_state.value.password)
            val confirmPassword =
                validateConfirmPassword.validateConfirmPassword(
                    _state.value.password,
                    _state.value.confirmPassword,
                )

            val isError =
                listOf(
                    nameResult,
                    passwordResult,
                    confirmPassword,
                ).any { !it.successful }

            if (isError) {
                _state.value =
                    _state.value.copy(
                        nameError = nameResult.errorMessage,
                        passwordError = passwordResult.errorMessage,
                        confirmPasswordError = confirmPassword.errorMessage,
                    )
                return false
            } else {
                return true
            }
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
                                val name = result.data?.name ?: ""
                                _state.value = _state.value.copy(name = name)
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
    }
