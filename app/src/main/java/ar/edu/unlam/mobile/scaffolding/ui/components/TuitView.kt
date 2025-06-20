package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import coil.compose.AsyncImage

@Composable
fun TuitView(tuit: Post) {
    Card(
        //horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier =
            Modifier
                .padding(3.dp)
                .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(4.dp),
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if (tuit.avatarUrl.isNotEmpty()) {
                    AsyncImage(
                        model = tuit.avatarUrl,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.DarkGray),
                    )
                }else {
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = null,
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                    )
                }
                Text(
                    text = tuit.author,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.alignByBaseline().padding(top = 8.dp)
                )
                Text(
                    text = tuit.date,
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.alignByBaseline(),
                )
            }

            Text(
                text = tuit.message,
                color = Color.Black,
                fontSize = 16.sp,
            )

            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                Icon(
                    imageVector = if (tuit.liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Likes",
                    tint = if (tuit.liked) Color.Red else Color.Black,
                    modifier = Modifier.size(24.dp),
                )
                Text(
                    text = tuit.likes.toString(),
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                )
                //Icon(imageVector = Icons.Outlined.AddComment, contentDescription = "Comments")
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
    TuitView(tuit)
}
