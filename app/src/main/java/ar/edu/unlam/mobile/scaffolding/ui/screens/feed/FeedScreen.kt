package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import ar.edu.unlam.mobile.scaffolding.ui.components.PostView
import ar.edu.unlam.mobile.scaffolding.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(feedViewModel: FeedViewModel = hiltViewModel()) {
    val uiState by feedViewModel.uiState.collectAsState()

    val isRefreshing by feedViewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullToRefreshState()
    val onRefresh = {
        Log.d("FeedScreen", "Refreshing start")
        feedViewModel.refreshPosts()
    }

    fun onLikePost(
        id: Int,
        liked: Boolean,
    ) {
        feedViewModel.isLikePost(id, liked)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    feedViewModel.refreshPosts(false)
                }
            }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

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
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    color = MaterialTheme.colorScheme.onPrimary,
                    state = refreshState,
                )
            },
        ) {
            when (val state = uiState.messageState) {
                is MessageUIState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
                is MessageUIState.Success -> {
                    LazyColumn(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                    ) {
                        items(state.posts) {
                                post ->
                            PostView(
                                post = post,
                                onLikeClick = { onLikePost(post.id, post.liked) },
                            )
                        }
                        Log.d("Cantidad actual de posts:", "${state.posts.size}")
                        // TODO Los post deverian ser clikeable para abrir el post
                    }
                }
                is MessageUIState.Error -> {
                    Text(
                        text = state.message,
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen()
}
