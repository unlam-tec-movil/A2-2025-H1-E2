package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getFeed(
        userToken: String,
        page: Int,
        onlyParents: Boolean,
    ): Flow<Resource<List<Post>>>
}
