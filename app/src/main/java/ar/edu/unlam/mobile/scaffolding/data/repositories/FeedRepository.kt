package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FeedRepository  {
    fun getFeed(
        userToken: String,
        appToken: String,
        page: Int,
        onlyParents: Boolean
    ): Flow<Resource<List<Post>>>
}