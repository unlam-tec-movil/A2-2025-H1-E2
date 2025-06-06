package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun signUpUser(user: User): Flow<Boolean>

    fun editUser(editedUser: User): Flow<Boolean>
}
