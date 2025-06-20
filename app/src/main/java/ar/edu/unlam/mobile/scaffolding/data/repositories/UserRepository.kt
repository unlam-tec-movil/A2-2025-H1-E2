package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.model.UserProfileModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun signUpUser(
        name: String,
        email: String,
        password: String,
    ): Flow<Resource<String>>

    fun loginUser(
        email: String,
        password: String,
    ): Flow<Resource<String>>

    fun getCurrentUser(): Flow<Resource<UserProfileModel>>

    fun editUser(
        name: String,
        avatarURL: String,
        email: String,
    ): Flow<Resource<String>>
}
