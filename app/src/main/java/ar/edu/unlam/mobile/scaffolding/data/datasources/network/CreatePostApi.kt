package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.data.model.CreatePostModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CreatePostApi {
    @POST("v1/me/tuits/")
    suspend fun createPost(
        @Header("Authorization") userToken: String,
        @Header("Application-Token") appToken: String,
        @Body createPostModel: CreatePostModel
    ): Call<Unit>//De momento no hace mucho xD
}