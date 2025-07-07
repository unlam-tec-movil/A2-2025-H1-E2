package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserEntity
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

    suspend fun isUserLogged(): Boolean

    suspend fun getUserFromDataBase(): UserEntity?

    fun editUser(
        name: String,
        avatarURL: String,
        password: String,
    ): Flow<Resource<String>>

    fun logoutUser(): Flow<Resource<Boolean>>

    suspend fun getEmailLogged(): String
}
