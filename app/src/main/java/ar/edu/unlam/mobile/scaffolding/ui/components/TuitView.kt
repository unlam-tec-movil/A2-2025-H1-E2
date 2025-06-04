package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
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
import coil.compose.rememberAsyncImagePainter
import models.Tuit

@Composable
fun TuitView(tuit: Tuit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier =
            Modifier
                .background(Color.White)
                .padding(4.dp)
                .fillMaxWidth(),
    ) {
        if (tuit.avatarUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(tuit.avatarUrl),
                contentDescription = null,
                modifier =
                    Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = tuit.author,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.alignByBaseline(),
                )

                Text(
                    text = tuit.date,
                    fontSize = 6.sp,
                    color = Color.Gray,
                    modifier = Modifier.alignByBaseline(),
                )
            }

            Text(
                text = tuit.message,
                fontSize = 10.sp,
            )

            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                Icon(
                    imageVector = if (tuit.liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Likes",
                    tint = if (tuit.liked) Color.Red else Color.Black,
                    modifier = Modifier.size(12.dp),
                )
                Text(
                    text = tuit.likes.toString(),
                    fontSize = 10.sp,
                )
            }
        }
    }
}

@Preview
@Composable
fun TuitViewPreview() {
    val tuit =
        Tuit(
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
