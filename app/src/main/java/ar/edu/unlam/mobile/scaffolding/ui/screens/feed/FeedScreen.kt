package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import ar.edu.unlam.mobile.scaffolding.ui.components.PostView
import ar.edu.unlam.mobile.scaffolding.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(feedViewModel: FeedViewModel = hiltViewModel()) {
    val posts by feedViewModel.feedState.collectAsState()
    Log.d("Cantidad actual de posts:", "${posts.size}")

    val isRefreshing by feedViewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullToRefreshState()
    val onRefresh = {
        Log.d("FeedScreen", "PullToRefresh: onRefresh triggered")
        feedViewModel.refreshPosts()
    }

    val tuits =
        listOf(
            Post(
                id = 1,
                message = "This is a sample tuit message.",
                parentId = 0,
                author = "Jo",
                avatarUrl = "https://fotos.perfil.com/2023/06/13/trim/720/410/messi-copa-del-mundo-1588008.jpg?webp",
                likes = 10,
                liked = false,
                date = "2023-10-01",
            ),
            Post(
                id = 1,
                message = "This is a sample tuit message.",
                parentId = 0,
                author = "John Doe",
                avatarUrl = "https://fotos.perfil.com/2023/06/13/trim/720/410/messi-copa-del-mundo-1588008.jpg?webp",
                likes = 10,
                liked = false,
                date = "2023-10-01",
            ),
            Post(
                id = 1,
                message = "This is a sample tuit message.",
                parentId = 0,
                author = "Doe",
                avatarUrl = "https://fotos.perfil.com/2023/06/13/trim/720/410/messi-copa-del-mundo-1588008.jpg?webp",
                likes = 10,
                liked = false,
                date = "2023-10-01",
            ),
            Post(
                id = 1,
                message = "This is a sample tuit message.",
                parentId = 0,
                author = "Doe",
                avatarUrl = "https://fotos.perfil.com/2023/06/13/trim/720/410/messi-copa-del-mundo-1588008.jpg?webp",
                likes = 10,
                liked = false,
                date = "2023-10-01",
            ),
            Post(
                id = 1,
                message = "This is a sample tuit message.",
                parentId = 0,
                author = "deberia funcionar",
                avatarUrl = "https://fotos.perfil.com/2023/06/13/trim/720/410/messi-copa-del-mundo-1588008.jpg?webp",
                likes = 10,
                liked = false,
                date = "2023-10-01",
            ),
        )
    Scaffold(
        topBar = { TopBar("Feed", null) },
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            state = refreshState,
            modifier =
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isRefreshing,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    state = refreshState,
                )
            },
        ) {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
            ) {
                items(tuits) { tuit ->
                    PostView(
                        post = tuit,
                        onInsertClick = {
                            feedViewModel.insertUserFav(
                                author = tuit.author,
                                avatarUrl = tuit.avatarUrl,
                            )
                        },
                    )
                }
                // el item tuit deveria ser clikeable para abrir el post, en una pantalla unica para verlo en detalle
            }
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen()
}
