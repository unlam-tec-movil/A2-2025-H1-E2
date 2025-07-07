package ar.edu.unlam.mobile.scaffolding.data.repositories

import androidx.paging.PagingData
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import kotlinx.coroutines.flow.Flow

interface FeedRepositoryTest {
    fun getFeed(): Flow<PagingData<Post>>
}
