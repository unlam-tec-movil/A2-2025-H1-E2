package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.FeedApi
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import javax.inject.Inject


class FeedRepository @Inject constructor(
    private val feedApi: FeedApi
)  {
    suspend fun getFeed(userToken: String, appToken: String, page: Int, onlyParents: Boolean) : List<Post>{
        return feedApi.getFeed(userToken, appToken, page, onlyParents)
    }
}