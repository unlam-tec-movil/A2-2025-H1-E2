package ar.edu.unlam.mobile.scaffolding.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.UserDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.UNLaMSocialApi
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import okio.IOException
import javax.inject.Inject

class PostPagingSource
    @Inject
    constructor(
        private val api: UNLaMSocialApi,
        private val userDao: UserDao,
    ) : PagingSource<Int, Post>() {
        override fun getRefreshKey(state: PagingState<Int, Post>): Int? = state.anchorPosition

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> =
            try {
                val currentUserToken = userDao.getUser()!!.userToken

                val page = params.key ?: 1
                val data =
                    api.getFeedTest(
                        userToken = currentUserToken,
                        page = page,
                        onlyParents = false,
                    )

                val netKey = if (data.size < 10) null else page + 1
                val prevKey = if (page == 1) null else page - 1

                LoadResult.Page(data = data, prevKey = prevKey, nextKey = netKey)
            } catch (exception: IOException) {
                LoadResult.Error(exception)
            }
    }
