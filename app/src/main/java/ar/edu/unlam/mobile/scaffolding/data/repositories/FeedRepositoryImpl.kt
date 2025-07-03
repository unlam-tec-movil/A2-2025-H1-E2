package ar.edu.unlam.mobile.scaffolding.data.repositories

import android.util.Log
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.UserDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.UserFavDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.ErrorResponse
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class FeedRepositoryImpl
    @Inject
    constructor(
        private val api: UNLaMSocialApi,
        private val userDao: UserDao,
        private val userFavDao: UserFavDao,
    ) : FeedRepository {
        override fun getFeed(
            page: Int,
            onlyParents: Boolean,
        ): Flow<Resource<List<Post>>> =
            flow {
                val response: Resource<List<Post>> =
                    try {
                        val currentUserToken = userDao.getUser()?.userToken
                        if (currentUserToken.isNullOrBlank()) {
                            Resource.Error(
                                data = null,
                                message = Log.e("API call", "Error: No hay token").toString(),
                            )
                        } else {
                            val data =
                                api.getFeed(
                                    userToken = currentUserToken,
                                    page = page,
                                    onlyParents = onlyParents,
                                )
                            Resource.Success(data)
                        }
                    } catch (e: HttpException) {
                        val errorMessage =
                            try {
                                val errorBody = e.response()?.errorBody()?.string()
                                if (errorBody != null) {
                                    val gson = Gson()
                                    gson.fromJson(errorBody, ErrorResponse::class.java).message
                                } else {
                                    e.message()
                                }
                            } catch (e: Exception) {
                                e.message
                            }
                        Resource.Error(message = errorMessage.toString())
                    } catch (e: okio.IOException) {
                        Resource.Error(message = e.message.toString())
                    } catch (e: Exception) {
                        Resource.Error(message = e.message.toString())
                    }
                emit(response)
            }

        override suspend fun insertFavUser(userFavEntity: UserFavEntity) {
            userFavDao.insertUserFav(userFavEntity)
        }
    }
