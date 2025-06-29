package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.HorizontalDivider
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
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.UserFavEntity
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import coil.compose.AsyncImage

@Composable
fun UserFavView(
    userFav: UserFavEntity,
    onDeleteClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .padding(3.dp)
                .fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(4.dp),
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if (userFav.avatarUrl.isNotEmpty()) {
                    AsyncImage(
                        model = userFav.avatarUrl,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.DarkGray),
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = null,
                        tint = Color.DarkGray,
                        modifier =
                            Modifier
                                .size(36.dp)
                                .clip(CircleShape),
                    )
                }
                Text(
                    text = userFav.author,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier =
                        Modifier
                            .alignByBaseline()
                            .padding(top = 8.dp),
                )
                IconButton(
                    modifier =
                        Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .size(20.dp),
                    onClick = {
                        onDeleteClick()
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_bookmark_remove_24),
                        contentDescription = "save",
                        tint = Color.Black,
                    )
                }
            }
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                thickness = 1.dp,
            )
        }
    }
}

@Preview
@Composable
fun UserFavViewPreview() {
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

    val user = UserFavEntity(author = tuit.author, avatarUrl = tuit.avatarUrl)
}
