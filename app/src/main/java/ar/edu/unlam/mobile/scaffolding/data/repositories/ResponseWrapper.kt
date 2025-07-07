package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import com.google.gson.annotations.SerializedName

class ResponseWrapper(
    @SerializedName("result") val result: List<Post>,
)
