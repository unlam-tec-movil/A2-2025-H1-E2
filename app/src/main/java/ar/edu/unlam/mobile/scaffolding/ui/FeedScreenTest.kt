package ar.edu.unlam.mobile.scaffolding.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import ar.edu.unlam.mobile.scaffolding.ui.components.PostView

@Composable
fun FeedScreenTest(feedScreenTestViewModel: FeedScreenTestViewModel = hiltViewModel()) {
    val posts = feedScreenTestViewModel.post.collectAsLazyPagingItems()
    val userName = feedScreenTestViewModel.userName.collectAsState()
    val usersFav = feedScreenTestViewModel.usersFavName.collectAsState()

    when {
        posts.loadState.refresh is LoadState.Loading && posts.itemCount == 0 -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = Color.White,
                )
            }
        }

        posts.loadState.refresh is LoadState.NotLoading && posts.itemCount == 0 -> {
            Text(text = "Ya no quedan mas post")
        }

        posts.loadState.hasError -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Ups ha ocurrido alo inesperado ",
                    color = Color.White,
                    fontSize = 20.sp,
                )
            }
        }
        else -> {
            PostList(posts, feedScreenTestViewModel, usersFav, userName)
            if (posts.loadState.append is LoadState.Loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
fun PostList(
    posts: LazyPagingItems<Post>,
    feedScreenTestViewModel: FeedScreenTestViewModel,
    usersFav: State<List<String>>,
    userName: State<String?>,
) {
    LazyColumn {
        items(posts.itemCount) {
            posts[it]?.let { characterModel: Post ->
                ItemList(characterModel, feedScreenTestViewModel, usersFav, userName)
            }
        }
    }
}

@Composable
fun ItemList(
    post: Post,
    feedScreenTestViewModel: FeedScreenTestViewModel,
    usersFav: State<List<String>>,
    userName: State<String?>,
) {
    val follow = post.author in usersFav.value

    PostView(
        post = post,
        isFollowable = post.author != userName.value,
        onLikeClick = { feedScreenTestViewModel.isLikePost(post.id, post.liked) },
        onClickAction = { feedScreenTestViewModel.goToDetail(post.id) },
        follow = follow,
        onFollowClick = {
            feedScreenTestViewModel.insertUserFav(
                author = post.author,
                avatarUrl = post.avatarUrl,
            )
        },
    )
}
