package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.SignUpRequest
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
                    val response =
                        api.signUpUser(
                            request = SignUpRequest(name, email, password),
                        )
                    emit(Resource.Success(data = response.token))
                } catch (e: HttpException) {
                    emit(Resource.Error(message = "Error"))
                }
            }

        override fun loginUser(
            email: String,
            password: String,
        ): Flow<Resource<String>> =
            flow {
                try {
                    val response =
                        api.loginUser(
                            request = LoginRequest(email, password),
                        )
                    emit(Resource.Success(data = response.token))
                } catch (e: HttpException) {
                    emit(Resource.Error(message = "Error"))
                }
            }

        override fun getCurrentUser(): Flow<Resource<UserProfileModel>> =
            flow {
                try {
                    val response =
                        api.getProfile(
                            // ToDo: Obtener token de base de datos
                            userToken = "",
                        )
                    emit(Resource.Success(data = response))
                } catch (e: HttpException) {
                    emit(Resource.Error(message = "Error"))
                }
            }

        override fun editUser(
            name: String,
            avatarURL: String,
            email: String,
        ): Flow<Resource<Boolean>> =
            flow {
                try {
                    val response =
                        api.signUpUser(
                            request = SignUpRequest(name, avatarURL, email),
                        )
                    emit(Resource.Success(data = true))
                } catch (e: HttpException) {
                    emit(Resource.Error(message = "Error"))
                }
            }
    }
