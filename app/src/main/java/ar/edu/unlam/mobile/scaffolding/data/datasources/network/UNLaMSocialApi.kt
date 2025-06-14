package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.EditUserRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.SignUpRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.TokenResponse
import ar.edu.unlam.mobile.scaffolding.data.model.UserProfileModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface UNLaMSocialApi {
    @POST("/login")
    suspend fun loginUser(
        @Header("Application-Token") appToken: String = API_KEY,
        @Body request: LoginRequest,
    ): TokenResponse

    @POST("/users")
    suspend fun signUpUser(
        @Header("Application-Token") appToken: String = API_KEY,
        @Body request: SignUpRequest,
    ): TokenResponse

    @GET("/me/profile")
    suspend fun getProfile(
        @Header("Authorization") userToken: String,
        @Header("Application-Token") appToken: String = API_KEY,
    ): UserProfileModel

    @PUT("/me/profile")
    suspend fun editProfile(
        @Header("Authorization") userToken: String,
        @Header("Application-Token") appToken: String = API_KEY,
        @Body request: EditUserRequest,
    ): TokenResponse

    companion object {
        const val BASE_URL = "https://tuiter.fragua.com.ar/api/v1"
        const val API_KEY = "a525ebbe00449a9855d0b41332a506ca820405ea8cfd148292a632c0ab7b8dce"
    }
}
