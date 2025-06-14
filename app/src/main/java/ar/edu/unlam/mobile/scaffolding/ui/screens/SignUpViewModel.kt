package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private var userJob: Job? = null

        fun signUpUser(
            name: String,
            email: String,
            password: String,
        ) {
            viewModelScope.launch {
                userJob?.cancel()
                userJob =
                    userRepository
                        .signUpUser(name, email, password)
                        .launchIn(CoroutineScope(Dispatchers.IO))
            }
        }
    }
