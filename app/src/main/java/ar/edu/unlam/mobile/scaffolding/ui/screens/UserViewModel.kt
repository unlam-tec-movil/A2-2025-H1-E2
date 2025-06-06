package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.model.User
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _currentUserState = mutableStateOf(User())
        val currentUserState: State<User> = _currentUserState

        private var userJob: Job? = null

        fun signUpUser() {
            _currentUserState.value
            viewModelScope.launch {
                userJob?.cancel()
                userJob =
                    userRepository
                        .signUpUser(
                            _currentUserState.value,
                        ).launchIn(CoroutineScope(Dispatchers.IO))
            }
        }

        fun editUser() {
            viewModelScope.launch {
                userJob?.cancel()
                userJob =
                    userRepository
                        .editUser(
                            _currentUserState.value,
                        ).launchIn(CoroutineScope(Dispatchers.IO))
            }
        }
    }
