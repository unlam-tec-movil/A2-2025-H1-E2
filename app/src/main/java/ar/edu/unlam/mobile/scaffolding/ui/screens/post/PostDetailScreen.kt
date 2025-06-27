package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import ar.edu.unlam.mobile.scaffolding.ui.components.TopBar
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.data.Resource

@Composable
fun PostDetailScreen(
    viewModel: PostDetailViewModel = hiltViewModel(),
    postId: Int,
    onBack: () -> Unit
) {
    val postResource = viewModel.post.collectAsState().value

    LaunchedEffect(postId) {
        viewModel.getPost(postId)
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
                    Text(
                        text = post.message,
                        modifier = Modifier.padding(paddingValues),
                    )
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

//
//@Preview
//@Composable
//fun PostDetailScreenPreview() {
//    val samplePost = Post(
//        id = "1",
//        title = "Sample Post",
//        content = "This is a sample post content.",
//        author = "Author Name",
//        timestamp = "2023-10-01T12:00:00Z"
//    )
////    val sampleReplies = listOf(
////        Replies("Reply 1", "Author 1"),
////        Replies("Reply 2", "Author 2")
////    )
//
//    PostDetailScreen(post = samplePost, onBack = {})
//}
