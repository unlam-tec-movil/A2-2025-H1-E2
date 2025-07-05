package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import android.util.Log
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
    Log.e("API call", "la cantidad de usuariso es ${usersFavName.size}")

    LaunchedEffect(postId) {
        viewModel.getPost(postId)
        viewModel.getPostReplies(postId)
    }

    PostDetailContent(
        navController = navController,
        postResource = postResource,
        userName = userName.value ?: "",
        usersFavName = usersFavName,
        viewModel = viewModel,
        repliesResource = repliesResource,
        onBack = onBack,
        onReply = { message ->
            viewModel.sendReply(postId, message)
        },
    )
}

@Composable
private fun PostDetailContent(
    navController: NavController = rememberNavController(),
    postResource: Resource<Post>?,
    repliesResource: Resource<List<Post>>,
    onBack: () -> Unit = {},
    onReply: (String) -> Unit = {},
    viewModel: PostDetailViewModel,
    userName: String,
    usersFavName: List<String>,
) {
    @Composable
    fun PostReplies(
        post: Post,
        replies: List<Post>,
        modifier: Modifier = Modifier,
    ) {
        LazyColumn(
            modifier =
                modifier
                    .fillMaxWidth(),
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
                        onFollowClick = {
                            viewModel.insertUserFav(
                                author = post.author,
                                avatarUrl = post.avatarUrl,
                            )
                        },
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
                    modifier = Modifier.scale(0.9f),
                    follow = follow,
                    isFollowable = post.author != userName,
                    onFollowClick = {
                        viewModel.insertUserFav(
                            author = reply.author,
                            avatarUrl = reply.avatarUrl,
                        )
                    },
                    onClickAction = { navController.navigate("postDetail/${reply.id}") },
                )
            }
        }
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
                                PostReplies(
                                    post,
                                    replies,
                                    modifier = Modifier.padding(paddingValues),
                                )
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

@Preview
@Composable
fun PostDetailContentPreview() {
    PostDetailContent(
        postResource =
            Resource.Success(
                Post(1, "Este es un post de prueba", 1, "Juliana", "", 1, false, "1-1-2023"),
            ),
        repliesResource =
            Resource.Success(
                listOf(
                    Post(2, "Primer reply", 1, "Carlos", "", 1, false, "2-1-2023"),
                    Post(3, "Segundo reply", 1, "Ana", "", 1, false, "3-1-2023"),
                ),
            ),
        viewModel = hiltViewModel(),
        userName = "",
        usersFavName = emptyList(),
    )
}
