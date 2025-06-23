package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.model.UserProfileModel
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserEditViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        val currentUser: MutableLiveData<UserProfileModel> by lazy {
            MutableLiveData<UserProfileModel>()
        }

        private var editUserJob: Job? = null
        private var getCurrentUserJob: Job? = null

        init {
            // Obtener los datos del usuario para cargar los campos con la información
            getCurrentUserJob?.cancel()
            getCurrentUserJob =
                viewModelScope.launch {
                    userRepository.getCurrentUser().collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                currentUser.value =
                                    currentUser.value.copy(
                                        name = result.data?.name ?: "",
                                        avatarURL = result.data?.avatarURL ?: "",
                                    )
                            }
                            is Resource.Error -> Log.e("API call", result.message ?: "Error 400 - Bad Request")
                        }
                    }
                }
        }

        fun editUser(
            name: String,
            password: String,
        ) {
            viewModelScope.launch {
                editUserJob?.cancel()
                editUserJob =
                    userRepository
                        .editUser(name, currentUser.value.avatarURL, password)
                        .launchIn(CoroutineScope(Dispatchers.IO))
            }
        }
    }
