package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.model.User
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        val currentUser: MutableLiveData<User> by lazy {
            MutableLiveData<User>()
        }

        private var userJob: Job? = null

        fun signUpUser() {
            viewModelScope.launch {
            }
        }

        fun editUser() {
        }
    }
