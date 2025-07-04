package ar.edu.unlam.mobile.scaffolding.domain.post.model

import com.google.gson.annotations.SerializedName

data class Post(
    val id: Int,
    val message: String,
    @SerializedName("parent_id") val parentId: Int,
    val author: String,
    @SerializedName("avatar_url") val avatarUrl: String = "",
    val likes: Long,
    val liked: Boolean,
    val date: String,
    var follow: Boolean = false,
)
