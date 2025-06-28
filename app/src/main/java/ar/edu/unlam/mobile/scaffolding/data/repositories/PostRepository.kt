package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun createPosts(message: String): Flow<Resource<String>>

    fun likePost(
        postId: Int,
        liked: Boolean,
    ): Flow<Resource<String>>
}
