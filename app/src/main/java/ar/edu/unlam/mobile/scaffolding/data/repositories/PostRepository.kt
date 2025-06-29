package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun createPosts(message: String): Flow<Resource<String>>

    fun likePost(
        postId: Int,
        liked: Boolean,
    ): Flow<Resource<String>>

    fun getPost(id: Int): Flow<Resource<Post>>

    fun getPostReplies(postId: Int): Flow<Resource<List<Post>>>

    fun sendReply(
        postId: Int,
        message: String,
    ): Flow<Resource<String>>
}
