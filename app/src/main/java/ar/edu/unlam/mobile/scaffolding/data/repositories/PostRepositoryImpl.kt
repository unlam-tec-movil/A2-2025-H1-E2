package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
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
    constructor(private val api: UNLaMSocialApi) : PostRepository {
        override fun createPosts(
            userToken: String,
            message: String,
        ): Flow<Resource<String>> =
            flow {
                val response =
                    try {
                        val data =
                            api.createPost(
                                userToken = userToken,
                                createPostRequest = CreatePostRequest(message),
                            )
                        Resource.Success(data.message)
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
