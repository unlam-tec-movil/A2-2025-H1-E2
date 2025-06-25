package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.BuildConfig
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.CreatePostRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.EditUserRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.SignUpRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.CreatePostResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.UserResponse
import ar.edu.unlam.mobile.scaffolding.data.model.UserProfileModel
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UNLaMSocialApi {
    @POST("login")
    suspend fun loginUser(
        @Header("Application-Token") appToken: String = API_KEY,
        @Body request: LoginRequest,
    ): UserResponse

    @POST("users")
    suspend fun signUpUser(
        @Header("Application-Token") appToken: String = API_KEY,
        @Body request: SignUpRequest,
    ): UserResponse

    @GET("me/profile")
    suspend fun getProfile(
        @Header("Authorization") userToken: String,
        @Header("Application-Token") appToken: String = API_KEY,
    ): UserProfileModel

    @PUT("me/profile")
    suspend fun editProfile(
        @Header("Authorization") userToken: String,
        @Header("Application-Token") appToken: String = API_KEY,
        @Body request: EditUserRequest,
    ): UserProfileModel

    @GET("me/feed")
    suspend fun getFeed(
        @Header("Authorization") userToken: String,
        @Header("Application-Token") appToken: String = API_KEY,
        @Query("page") page: Int,
        @Query("only_parents") onlyParents: Boolean = false,
    ): List<Post>

    @POST("me/tuits")
    suspend fun createPost(
        @Header("Authorization") userToken: String,
        @Header("Application-Token") appToken: String = API_KEY,
        @Body createPostRequest: CreatePostRequest,
    ): CreatePostResponse

    companion object {
        const val BASE_URL = "https://tuiter.fragua.com.ar/api/v1/"
        const val API_KEY = BuildConfig.API_KEY
    }
}
