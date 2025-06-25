package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun createPosts(
        userToken: String,
        message: String,
    ): Flow<Resource<String>>
}
