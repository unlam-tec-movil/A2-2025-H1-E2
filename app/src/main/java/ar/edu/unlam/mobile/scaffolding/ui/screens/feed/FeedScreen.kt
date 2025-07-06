package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
    val userName = feedViewModel.userName.collectAsState()
    val usersFav = feedViewModel.usersFavName.collectAsState()

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
                        val maxValue = 1_000_000 // Valor máximo para el bucle infinito
                        val itemCount = if (uiState.finalPage) maxValue else uiState.posts.size

                        items(itemCount) { index ->
                            // Para repetir los ítems en bucle, usamos el operador % (módulo), que devuelve el resto de una división.
                            // Ejemplo: si tengo 5 ítems y el índice es 7 → hago 7 % 5 = 2.
                            // Entonces uso el ítem en posición 2.
                            // Así, aunque el índice crezca, siempre vuelve a empezar desde el inicio de la lista.

                            val realIndex =
                                if (uiState.finalPage) {
                                    // Si ya se llegó a la última página, habilitar el bucle
                                    index % uiState.posts.size
                                } else {
                                    index // sino muestra el índice real
                                }

                            val post = uiState.posts[realIndex]

                            val follow = post.author in usersFav.value
                            val index = uiState.posts.indexOf(post)
                            if (index == uiState.posts.lastIndex && !uiState.isLoadingMore && !uiState.isRefreshing) {
                                // Carga mas posts cuando se llega al final de la lista
                                feedViewModel.loadMorePosts()
                            }
                            PostView(
                                post = post,
                                isFollowable = post.author != userName.value,
                                onLikeClick = { feedViewModel.isLikePost(post.id, post.liked) },
                                onClickAction = { feedViewModel.goToDetail(post.id) },
                                follow = follow,
                                onFollowClick = {
                                    feedViewModel.insertUserFav(
                                        author = post.author,
                                        avatarUrl = post.avatarUrl,
                                    )
                                },
                            )
                        }

                        // Mostrar un indicador de carga al final de la lista si se está cargando más
                        item {
                            if (uiState.isLoadingMore) {
                                Box(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    CircularProgressIndicator(
                                        modifier =
                                            Modifier
                                                .padding(16.dp),
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            }
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
