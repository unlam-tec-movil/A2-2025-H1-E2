package ar.edu.unlam.mobile.scaffolding.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.UserDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedRepositoryTestImpl
    @Inject
    constructor(
        private val api: UNLaMSocialApi,
        private val userDao: UserDao,
    ) : FeedRepositoryTest {
        companion object {
            const val MAX_POST = 10
            const val PREFETCH_ITEMS = 3
        }

        override fun getFeed(): Flow<PagingData<Post>> =
            Pager(
                config = PagingConfig(pageSize = MAX_POST, prefetchDistance = PREFETCH_ITEMS),
                pagingSourceFactory = {
                    PostPagingSource(api, userDao = userDao)
                },
            ).flow
    }
