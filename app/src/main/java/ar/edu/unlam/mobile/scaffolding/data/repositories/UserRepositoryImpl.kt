package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.model.User
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl : UserRepository {
    // TODO: Agregar datasources
    override fun signUpUser(user: User): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun editUser(editedUser: User): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}
