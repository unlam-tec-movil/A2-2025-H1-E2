package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import coil.compose.AsyncImage
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun PostView(post: Post) {
    Card(
        shape = RoundedCornerShape(7.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        modifier =
            Modifier
                .padding(2.dp)
                .fillMaxWidth(),
        onClick = {},
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(4.dp),
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary),
                    model = post.avatarUrl,
                    contentDescription = null,
                )
                Text(
                    text = post.author,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .alignByBaseline()
                        .padding(top = 8.dp),
                )
                Text(
                    text = post.date,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .alignByBaseline()
                        .padding(start = 3.dp),
                )
            }

            Text(
                text = post.message,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
            )

            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                IconButton(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .size(24.dp),
                    onClick = {},
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "Likes",
                        tint = if (post.liked) Color.Red else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    )
                }
                Text(
                    text = post.likes.toString(),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .width(50.dp)
                        .align(alignment = Alignment.CenterVertically)
                        .padding(end = 12.dp),
                )
                IconButton(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .size(23.dp),
                    onClick = {},
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chat_bubble), // Usando painterResource
                        contentDescription = "Comments",
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TuitViewPreview() {
    val tuit =
        Post(
            id = 1,
            message = "This is a sample tuit message.",
            parentId = 0,
            author = "John Doe",
            avatarUrl = "https://fotos.perfil.com/2023/06/13/trim/720/410/messi-copa-del-mundo-1588008.jpg?webp",
            likes = 10,
            liked = false,
            date = "2023-10-01",
        )
    PostView(tuit)
}
