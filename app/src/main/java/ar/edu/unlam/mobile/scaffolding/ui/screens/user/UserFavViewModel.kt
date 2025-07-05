package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserFavRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserFavViewModel
    @Inject
    constructor(
        private val repository: UserFavRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _userFavState = MutableStateFlow<List<UserFavEntity>>(emptyList())
        val userFavState: StateFlow<List<UserFavEntity>> = _userFavState.asStateFlow()

        private var userFavJob: Job? = null
        private var getFavJob: Job? = null
        private var userEmail: String = ""

        init {
            getUsers()
        }

        fun deleteAllUser() {
            userFavJob?.cancel()
            userFavJob =
                viewModelScope.launch {
                    repository.deleteAllUserFavByOwner(userEmail)
                }
        }

        fun deleteUser(userFavEntity: UserFavEntity) {
            userFavJob?.cancel()
            userFavJob =
                viewModelScope.launch {
                    repository.deleteUserFav(userFavEntity)
                }
        }

        private fun getUsers() {
            getFavJob?.cancel()
            getFavJob =
                viewModelScope.launch {
                    userEmail = userRepository.getEmailLogged()
                    repository.getAllFavUser(emailLogged = userEmail).collect { result ->
                        _userFavState.value = result
                    }
                }
        }
    }
