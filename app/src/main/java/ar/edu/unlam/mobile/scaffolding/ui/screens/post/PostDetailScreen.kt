package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.foundation.background
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


@Composable
fun PostDetailScreen(
    post: Post,
    replies: List<String> = emptyList(),
    onBack: () -> Unit
) {
    Scaffold(
        topBar = { TopBar("Post", onNavigateBack = {}) },
        modifier = Modifier.fillMaxWidth(),
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(paddingValues = paddingValues)
                    .padding(top = 24.dp),
        ) {
            LazyColumn {
                items(replies) { reply ->
//                    Column(modifier = Modifier.padding(8.dp)) {
//                        Text(reply.author, style = MaterialTheme.typography.labelMedium)
//                        Text(reply.content, style = MaterialTheme.typography.bodyMedium)
//                    }
                }
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
