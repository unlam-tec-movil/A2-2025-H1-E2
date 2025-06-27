package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun createPosts(message: String): Flow<Resource<String>>

    fun getPost(id: Int): Flow<Resource<List<Post>>>
}
