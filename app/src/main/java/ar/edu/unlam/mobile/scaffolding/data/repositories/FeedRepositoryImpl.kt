package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.ErrorResponse
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import coil.network.HttpException
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class FeedRepositoryImpl
@Inject
    constructor(
    private val api: UNLaMSocialApi,
    ) : FeedRepository {

        private val exceptionMsg = "Algo salió mal"
        private val internetConnectionErrorMsg = "Por favor, verificar la conexión a internet"

        override fun getFeed(
            userToken: String,
            appToken: String,
            page: Int,
            onlyParents: Boolean
        ): Flow<Resource<List<Post>>> =
            flow {
                try {
                    val response =
                        api.getFeed(
                            userToken,
                            appToken,
                            page,
                            onlyParents
                        )
                    emit(Resource.Success(data = response))
                }catch (e: HttpException) {
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
                    emit(Resource.Error(message = exceptionMsg))
                }
            }
    }