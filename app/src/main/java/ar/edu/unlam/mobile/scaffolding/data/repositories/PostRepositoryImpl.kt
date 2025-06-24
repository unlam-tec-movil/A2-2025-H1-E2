package ar.edu.unlam.mobile.scaffolding.data.repositories

import android.util.Log
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
        private val exceptionMsg = "Algo salió mal"
        private val internetConnectionErrorMsg = "Por favor, verificar la conexión a internet"

        override fun createPosts(
            userToken: String,
            appToken: String,
            message: String,
        ): Flow<Resource<String>> =
            flow {
                try {
                    val response =
                        api.createPost(
                            userToken,
                            appToken,
                            request = CreatePostRequest(message),
                        )
                    emit(Resource.Success(data = response.message))
                } catch (e: HttpException) {
                    val errorMessage =
                        try {
                            val errorBody = e.response.body?.string()
                            val gson = Gson()
                            gson.fromJson(errorBody, ErrorResponse::class.java).message
                        } catch (e: Exception) {
                            "$exceptionMsg - primer catch"
                        }
                    emit(Resource.Error(message = errorMessage))
                } catch (e: IOException) {
                    emit(Resource.Error(message = internetConnectionErrorMsg))
                } catch (e: Exception) {
                    val message = e.message ?: "$exceptionMsg - ultimo catch"
                    Log.i("API call", e.toString())
                    emit(Resource.Error(message = message))
                }
            }
    }
