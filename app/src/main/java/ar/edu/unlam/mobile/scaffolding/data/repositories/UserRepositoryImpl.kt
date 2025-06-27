package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.UserDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.EditUserRequest
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
        private val userDao: UserDao,
    ) : UserRepository {
        override fun signUpUser(
            name: String,
            email: String,
            password: String,
        ): Flow<Resource<String>> =
            flow {
                val result =
                    try {
                        val data =
                            api.signUpUser(
                                request = SignUpRequest(name, email, password),
                            )
                        val user =
                            UserEntity(
                                id = 1,
                                name = data.name,
                                email = data.email,
                                avatarUrl = null,
                                userToken = data.token,
                            )
                        userDao.insertUser(user)
                        Resource.Success(data.name)
                    } catch (e: HttpException) {
                        val errorMessage =
                            try {
                                val errorBody = e.response.body?.string()
                                val gson = Gson()
                                gson.fromJson(errorBody, ErrorResponse::class.java).message
                            } catch (e: Exception) {
                                e.message
                            }
                        Resource.Error(message = errorMessage.toString())
                    } catch (e: IOException) {
                        Resource.Error(message = e.message.toString())
                    } catch (e: Exception) {
                        Resource.Error(message = e.message.toString())
                    }
                emit(result)
            }

        override fun loginUser(
            email: String,
            password: String,
        ): Flow<Resource<String>> =
            flow {
                val result =
                    try {
                        val data =
                            api.loginUser(
                                request = LoginRequest(email, password),
                            )
                        val user =
                            UserEntity(
                                id = 1,
                                name = data.name,
                                email = data.email,
                                avatarUrl = null,
                                userToken = data.token,
                            )
                        userDao.insertUser(user)
                        Resource.Success(data.name)
                    } catch (e: HttpException) {
                        val errorMessage =
                            try {
                                val errorBody = e.response.body?.string()
                                val gson = Gson()
                                gson.fromJson(errorBody, ErrorResponse::class.java).message
                            } catch (e: Exception) {
                                e.message
                            }
                        Resource.Error(message = errorMessage.toString())
                    } catch (e: IOException) {
                        Resource.Error(message = e.message.toString())
                    } catch (e: Exception) {
                        Resource.Error(message = e.message.toString())
                    }
                emit(result)
            }

        override fun getCurrentUser(): Flow<Resource<UserProfileModel>> =
            flow {
                val result: Resource<UserProfileModel> =
                    try {
                        val currentUserToken = userDao.getUser()?.userToken
                        if (currentUserToken.isNullOrBlank()) {
                            Resource.Error(data = null, message = "No se encontró el token de usuario")
                        } else {
                            val data =
                                api.getProfile(
                                    userToken = currentUserToken,
                                )
                            Resource.Success(data)
                        }
                    } catch (e: HttpException) {
                        val errorMessage =
                            try {
                                val errorBody = e.response.body?.string()
                                val gson = Gson()
                                gson.fromJson(errorBody, ErrorResponse::class.java).message
                            } catch (e: Exception) {
                                e.message
                            }
                        Resource.Error(message = errorMessage.toString())
                    } catch (e: IOException) {
                        Resource.Error(message = e.message.toString())
                    } catch (e: Exception) {
                        Resource.Error(message = e.message.toString())
                    }
                emit(result)
            }

        override fun editUser(
            name: String,
            avatarURL: String,
            password: String,
        ): Flow<Resource<String>> =
            flow {
                val result: Resource<String> =
                    try {
                        val currentUserToken = userDao.getUser()?.userToken
                        if (currentUserToken.isNullOrBlank()) {
                            Resource.Error(data = null, message = "No se encontró el token de usuario")
                        } else {
                            val data =
                                api.editProfile(
                                    userToken = currentUserToken,
                                    request = EditUserRequest(name, avatarURL, password),
                                )
                            userDao.updateUser(name, avatarURL)
                            Resource.Success(data.name)
                        }
                    } catch (e: HttpException) {
                        val errorMessage =
                            try {
                                val errorBody = e.response.body?.string()
                                val gson = Gson()
                                gson.fromJson(errorBody, ErrorResponse::class.java).message
                            } catch (e: Exception) {
                                e.message
                            }
                        Resource.Error(message = errorMessage.toString())
                    } catch (e: IOException) {
                        Resource.Error(message = e.message.toString())
                    } catch (e: Exception) {
                        Resource.Error(message = e.message.toString())
                    }
                emit(result)
            }

        override fun logoutUser(): Flow<Resource<Boolean>> =
            flow {
                val result: Resource<Boolean> =
                    try {
                        userDao.logoutUser()
                        Resource.Success(true)
                    } catch (e: IOException) {
                        Resource.Error(message = e.message.toString())
                    } catch (e: Exception) {
                        Resource.Error(message = e.message.toString())
                    }
                emit(result)
            }
    }
