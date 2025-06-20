package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FeedApi {
    @GET("v1/me/feed")
    suspend fun getFeed(
        @Header("Authorization") userToken: String,
        @Header("Application-Token") appToken: String,
        @Query("page") page: Int,
        @Query("only_parents") onlyParents: Boolean = false
    ): List<Post>
}