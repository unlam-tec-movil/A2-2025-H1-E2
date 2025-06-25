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

        override fun getFeed(
            userToken: String,
            page: Int,
            onlyParents: Boolean,
        ): Flow<Resource<List<Post>>> =
            flow {
                val result =
                    try {
                        val data =
                            api.getFeed(
                                userToken = userToken,
                                page = page,
                                onlyParents = onlyParents,
                            )
                        Resource.Success(data)
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
    }
