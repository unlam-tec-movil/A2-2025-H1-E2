package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getFeed(
        page: Int,
        onlyParents: Boolean,
    ): Flow<Resource<List<Post>>>

    suspend fun insertFavUser(userFavEntity: UserFavEntity)
}
