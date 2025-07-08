package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import ar.edu.unlam.mobile.scaffolding.ui.components.PostView
import ar.edu.unlam.mobile.scaffolding.ui.components.ReplyTextField
import ar.edu.unlam.mobile.scaffolding.ui.components.TopBar

@Composable
fun PostDetailScreen(
    navController: NavController = rememberNavController(),
    viewModel: PostDetailViewModel = hiltViewModel(),
    postId: Int,
    onBack: () -> Unit,
) {
    val postResource = viewModel.post.collectAsState().value
    val repliesResource = viewModel.replies.collectAsState().value
    val userName = viewModel.userName
    val usersFavName = viewModel.usersFavName.collectAsState().value

    LaunchedEffect(postId) {
        viewModel.getPost(postId)
        viewModel.getPostReplies(postId)
    }

    Scaffold(
        topBar = { TopBar(stringResource(R.string.postScreenName), onNavigateBack = onBack) },
        modifier = Modifier.fillMaxWidth(),
    ) { paddingValues ->
        when (val result = postResource) {
            null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is Resource.Success -> {
                result.data?.let { post ->
                    when (repliesResource) {
                        is Resource.Success -> {
                            repliesResource.data?.let { replies ->
                                Box(modifier = Modifier.padding(paddingValues)) {
                                    postResource.data?.let {
                                        PostDetailContent(
                                            post = it,
                                            replies = repliesResource.data,
                                            userName = userName.value ?: "",
                                            usersFavName = usersFavName,
                                            navController = navController,
                                            onReply = { message ->
                                                viewModel.sendReply(
                                                    post.id,
                                                    message,
                                                )
                                            },
                                            viewModel = hiltViewModel(),
                                        )
                                    }
                                }
                            }
                        }

                        is Resource.Error -> {
                            Text(
                                text = repliesResource.message ?: "Error desconocido",
                                modifier = Modifier.padding(paddingValues),
                            )
                        }
                    }
                }
            }

            is Resource.Error -> {
                Text(
                    text = result.message ?: "Error desconocido",
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }
    }
}

@Composable
fun PostDetailContent(
    post: Post,
    replies: List<Post>,
    navController: NavController,
    onReply: (String) -> Unit = {},
    viewModel: PostDetailViewModel,
    userName: String,
    usersFavName: List<String>,
) {
    val userFav: (author: String, avatarUrl: String) -> Unit = { author, avatarUrl ->
        viewModel.insertUserFav(
            author,
            avatarUrl,
        )
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
        item {
            Column(
                modifier =
                    Modifier.background(
                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    ),
            ) {
                val follow = post.author in usersFavName

                PostView(
                    post = post,
                    isFollowable = post.author != userName,
                    follow = follow,
                    onLikeClick = {
                        viewModel.isLikePost(
                            postLikeId = post.id,
                            isLiked = post.liked,
                        )
                    },
                    onFollowClick = { userFav(post.author, post.avatarUrl) },
                    forcedExpanded = true,
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f),
                    thickness = 1.dp,
                )
                ReplyTextField(onReply = { message ->
                    onReply(message)
                })
            }
        }

        items(replies) { reply ->
            val follow = reply.author in usersFavName

            PostView(
                post = reply,
                isFollowable = reply.author != userName,
                follow = follow,
                modifier = Modifier.scale(scale = 0.9f),
                onLikeClick = {
                    viewModel.isLikePost(
                        postLikeId = reply.id,
                        isLiked = reply.liked,
                        mainPost = post.id,
                    )
                },
                onFollowClick = { userFav(reply.author, reply.avatarUrl) },
                onClickAction = { navController.navigate("postDetail/${reply.id}") },
            )
        }
    }
}

// No quite esto por si alguien despues quiere hacerlo funcionar
// Pero habria que crear unas verciones de los composable sin que llamen a hiltViewModel
@Preview
@Composable
fun PostDetailContentPreview() {
    val post = Post(id = 1, "Este es un post de prueba", 1, "Juliana", "", 1, false, "1-1-2023")
    val list =
        listOf(
            Post(id = 2, "Primer reply", 1, "Carlos", "", 1, false, "2-1-2023"),
            Post(id = 3, "Segundo reply", 1, "Ana", "", 1, false, "3-1-2023"),
        )
    PostDetailContent(
        post = post,
        replies = list,
        navController = rememberNavController(),
        onReply = {},
        viewModel = hiltViewModel(),
        userName = "",
        usersFavName = emptyList(),
    )
}
