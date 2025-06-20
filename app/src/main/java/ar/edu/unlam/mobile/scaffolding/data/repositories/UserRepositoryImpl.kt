package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.SignUpRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.ErrorResponse
import ar.edu.unlam.mobile.scaffolding.data.model.UserProfileModel
import coil.network.HttpException
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val api: UNLaMSocialApi,
    ) : UserRepository {
        private val exceptionMsg = "Algo salió mal"
        private val internetConnectionErrorMsg = "Por favor, verificar la conexión a internet"

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
                    // ToDo: Almacenar datos en base de datos
                    emit(Resource.Success(data = response.name))
                } catch (e: HttpException) {
                    val errorMessage =
                        try {
                            val errorBody = e.response.body?.string()
                            val gson = Gson()
                            gson.fromJson(errorBody, ErrorResponse::class.java).message
                        } catch (e: Exception) {
                            exceptionMsg
                        }
                    emit(Resource.Error(message = errorMessage))
                } catch (e: IOException) {
                    emit(Resource.Error(message = internetConnectionErrorMsg))
                } catch (e: Exception) {
                    emit(Resource.Error(message = exceptionMsg))
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
                    // ToDo: Almacenar datos en base de datos
                    emit(Resource.Success(data = response.name))
                } catch (e: HttpException) {
                    val errorMessage =
                        try {
                            val errorBody = e.response.body?.string()
                            val gson = Gson()
                            gson.fromJson(errorBody, ErrorResponse::class.java).message
                        } catch (e: Exception) {
                            exceptionMsg
                        }
                    emit(Resource.Error(message = errorMessage))
                } catch (e: IOException) {
                    emit(Resource.Error(message = internetConnectionErrorMsg))
                } catch (e: Exception) {
                    emit(Resource.Error(message = exceptionMsg))
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
                    val errorMessage =
                        try {
                            val errorBody = e.response.body?.string()
                            val gson = Gson()
                            gson.fromJson(errorBody, ErrorResponse::class.java).message
                        } catch (e: Exception) {
                            exceptionMsg
                        }
                    emit(Resource.Error(message = errorMessage))
                } catch (e: IOException) {
                    emit(Resource.Error(message = internetConnectionErrorMsg))
                } catch (e: Exception) {
                    emit(Resource.Error(message = exceptionMsg))
                }
            }

        override fun editUser(
            name: String,
            avatarURL: String,
            email: String,
        ): Flow<Resource<String>> =
            flow {
                try {
                    val response =
                        api.signUpUser(
                            request = SignUpRequest(name, avatarURL, email),
                        )
                    emit(Resource.Success(data = response.name))
                } catch (e: HttpException) {
                    val errorMessage =
                        try {
                            val errorBody = e.response.body?.string()
                            val gson = Gson()
                            gson.fromJson(errorBody, ErrorResponse::class.java).message
                        } catch (e: Exception) {
                            exceptionMsg
                        }
                    emit(Resource.Error(message = errorMessage))
                } catch (e: IOException) {
                    emit(Resource.Error(message = internetConnectionErrorMsg))
                } catch (e: Exception) {
                    emit(Resource.Error(message = exceptionMsg))
                }
            }
    }
