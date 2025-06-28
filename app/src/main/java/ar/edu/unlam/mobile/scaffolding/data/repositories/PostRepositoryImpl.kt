
package ar.edu.unlam.mobile.scaffolding.data.repositories

import android.util.Log
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.UserDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.CreatePostRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.ErrorResponse
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class PostRepositoryImpl
    @Inject
    constructor(
        private val api: UNLaMSocialApi,
        private val userDao: UserDao,
    ) : PostRepository {
        override fun createPosts(message: String): Flow<Resource<String>> =
            flow {
                val response: Resource<String> =
                    try {
                        val currentUserToken = userDao.getUser()?.userToken
                        if (currentUserToken.isNullOrBlank()) {
                            Resource.Error(data = null, message = Log.e("API call", "Error: No hay token").toString())
                        } else {
                            val data =
                                api.createPost(
                                    userToken = currentUserToken,
                                    createPostRequest = CreatePostRequest(message),
                                )
                            Resource.Success(data.message)
                        }
                    } catch (e: HttpException) {
                        val errorMessage =
                            try {
                                val errorBody = e.response()?.errorBody()?.string()
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
                emit(response)
            }

        override fun getPost(tuitId: Int): Flow<Resource<Post>> =
            flow {
                val token = userDao.getUser()?.userToken

                if (token.isNullOrBlank()) {
                    emit(Resource.Error(message = "Token de usuario no disponible"))
                    return@flow
                }

                try {
                    val post =
                        api.getPost(
                            userToken = token,
                            tuitId = tuitId,
                        )
                    emit(Resource.Success(data = post))
                } catch (e: HttpException) {
                    val errorMessage =
                        try {
                            val errorBody = e.response()?.errorBody()?.string()
                            val parsed = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            parsed.message
                        } catch (e: Exception) {
                            e.message ?: "Error desconocido"
                        }
                    emit(Resource.Error(message = errorMessage))
                } catch (e: IOException) {
                    emit(Resource.Error(message = "Error de red: ${e.message}"))
                } catch (e: Exception) {
                    emit(Resource.Error(message = "Error inesperado: ${e.message}"))
                }
            }

        override fun getPostReplies(tuitId: Int): Flow<Resource<List<Post>>> =
            flow {
                val token = userDao.getUser()?.userToken

                if (token.isNullOrBlank()) {
                    emit(Resource.Error(message = "Token de usuario no disponible"))
                    return@flow
                }

                try {
                    val replies =
                        api.getPostReplies(
                            userToken = token,
                            tuitId = tuitId,
                        )
                    emit(Resource.Success(data = replies))
                } catch (e: HttpException) {
                    val errorMessage =
                        try {
                            val errorBody = e.response()?.errorBody()?.string()
                            val parsed = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            parsed.message
                        } catch (e: Exception) {
                            e.message ?: "Error desconocido"
                        }
                    emit(Resource.Error(message = errorMessage))
                } catch (e: IOException) {
                    emit(Resource.Error(message = "Error de red: ${e.message}"))
                } catch (e: Exception) {
                    emit(Resource.Error(message = "Error inesperado: ${e.message}"))
                }
            }
    }
