package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(
        userToken: String,
        appToken: String,
    ): Flow<Resource<String>>
}