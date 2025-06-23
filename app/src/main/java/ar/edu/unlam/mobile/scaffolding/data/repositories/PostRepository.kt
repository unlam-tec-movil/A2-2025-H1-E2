package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun createPosts(
        userToken: String,
        appToken: String,
        message: String
    ): Flow<Resource<String>>
}
