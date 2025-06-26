package ar.edu.unlam.mobile.scaffolding.data.repositories

import android.util.Log
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.UserDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.CreatePostRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.ErrorResponse
import coil.network.HttpException
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
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
                emit(response)
            }
    }
