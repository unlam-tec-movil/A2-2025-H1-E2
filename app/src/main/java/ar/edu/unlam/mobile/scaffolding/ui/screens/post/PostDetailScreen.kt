package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.data.Resource
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import ar.edu.unlam.mobile.scaffolding.ui.components.PostView
import ar.edu.unlam.mobile.scaffolding.ui.components.ReplyTextField
import ar.edu.unlam.mobile.scaffolding.ui.components.TopBar

@Composable
fun PostDetailScreen(
    viewModel: PostDetailViewModel = hiltViewModel(),
    postId: Int,
    onBack: () -> Unit,
) {
    val postResource = viewModel.post.collectAsState().value
    val repliesResource = viewModel.replies.collectAsState().value

    LaunchedEffect(postId) {
        viewModel.getPost(postId)
        viewModel.getPostReplies(postId)
    }

    PostDetailContent(
        postResource = postResource,
        repliesResource = repliesResource,
        onBack = onBack,
    )
}

@Composable
private fun PostDetailContent(
    postResource: Resource<Post>?,
    repliesResource: Resource<List<Post>>,
    onBack: () -> Unit,
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
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
        ) {
            item {
                Column {
                    PostView(post = post)
                    ReplyTextField()
                }
            }
            items(replies) { reply ->
                PostView(
                    post = reply,
                    modifier =
                        Modifier
                            .scale(0.9f),
                )
            }
        }
    }

    Scaffold(
        topBar = { TopBar("Post", onNavigateBack = onBack) },
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
                                PostReplies(post, replies, modifier = Modifier.padding(paddingValues))
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
        onBack = {},
    )
}
