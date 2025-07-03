package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.PostView
import ar.edu.unlam.mobile.scaffolding.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    feedViewModel: FeedViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by feedViewModel.uiState.collectAsState()

    val onRefresh = {
        Log.d("FeedScreen", "Refreshing feed")
        feedViewModel.refreshPosts(pullToRefresh = true)
    }

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val refreshFeed =
        savedStateHandle
            ?.getLiveData<Boolean>("refresh_feed")
            ?.observeAsState()

    LaunchedEffect(refreshFeed?.value) {
        if (refreshFeed?.value == true) {
            onRefresh()
            savedStateHandle["refresh_feed"] = false
        }
    }

    LaunchedEffect(Unit) {
        feedViewModel.navigationEvent.collect { postId ->
            navController.navigate("postDetail/$postId")
        }
    }

    Scaffold(
        topBar = { TopBar(stringResource(R.string.feedScreenName), null) },
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = onRefresh,
            modifier =
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
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
                        items(state.posts) { post ->
                            PostView(
                                post = post,
                                onLikeClick = { feedViewModel.isLikePost(post.id, post.liked) },
                                onClickAction = { feedViewModel.goToDetail(post.id) },
                                onFollowClick = {
                                    feedViewModel.followUserFav(
                                        author = post.author,
                                        avatarUrl = post.avatarUrl,
                                    )
                                },
                            )
                        }
                    }
                }
                is MessageUIState.Error -> {
                    Column(modifier = Modifier.padding(6.dp)) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.onPrimary,
                                    contentColor = MaterialTheme.colorScheme.background,
                                ),
                            onClick = {
                                feedViewModel.refreshPosts(reloadScreen = true)
                            },
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen(
        navController = rememberNavController(),
    )
}
