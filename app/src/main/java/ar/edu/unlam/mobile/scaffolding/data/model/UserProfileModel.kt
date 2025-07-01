package ar.edu.unlam.mobile.scaffolding.data.model

import com.google.gson.annotations.SerializedName

data class UserProfileModel(
    val name: String = "",
    @SerializedName("avatar_url")
    val avatarURL: String = "",
    val email: String = "",
)
