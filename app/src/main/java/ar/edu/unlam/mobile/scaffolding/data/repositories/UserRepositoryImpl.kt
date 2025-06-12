package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.data.model.UserProfileModel
import coil.network.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val api: UNLaMSocialApi,
    ) : UserRepository {
        override fun signUpUser(
            name: String,
            email: String,
            password: String,
        ): Flow<Resource<String>> =
            flow {
                try {
                    emit(Resource.Success(data = null))
                } catch (e: HttpException) {
                    emit(Resource.Error(message = "Error"))
                }
                TODO("Not yet implemented")
            }

        override fun loginUser(
            email: String,
            password: String,
        ): Flow<Resource<String>> =
            flow {
                try {
                    emit(Resource.Success(data = null))
                } catch (e: HttpException) {
                    emit(Resource.Error(message = "Error"))
                }
                TODO("Not yet implemented")
            }

        override fun getCurrentUser(): Flow<Resource<UserProfileModel>> =
            flow {
                try {
                    emit(Resource.Success(data = null))
                } catch (e: HttpException) {
                    emit(Resource.Error(message = "Error"))
                }
                TODO("Not yet implemented")
            }

        override fun editUser(
            name: String,
            avatarURL: String,
            email: String,
        ): Flow<Resource<Boolean>> =
            flow {
                try {
                    emit(Resource.Success(data = null))
                } catch (e: HttpException) {
                    emit(Resource.Error(message = "Error"))
                }
                TODO("Not yet implemented")
            }
    }
