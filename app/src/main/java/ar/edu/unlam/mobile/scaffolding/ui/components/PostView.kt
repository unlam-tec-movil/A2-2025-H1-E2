package ar.edu.unlam.mobile.scaffolding.ui.components

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import ar.edu.unlam.mobile.scaffolding.domain.post.model.Post
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun PostView(
    modifier: Modifier = Modifier,
    post: Post,
    onLikeClick: () -> Unit = {},
    onClickAction: () -> Unit = {},
    onFollowClick: (() -> Unit?)? = null,
    isFollowable: Boolean = true,
) {
    val postTime =
        remember(post.date) {
            getTimeInterval(post.date)
        }
    Card(
        shape = RoundedCornerShape(9.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.8f,
                    ),
            ),
        modifier =
            modifier
                .padding(2.dp)
                .fillMaxWidth(),
        onClick = { onClickAction() },
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
                    modifier =
                        Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary),
                    model = post.avatarUrl,
                    contentDescription = null,
                )
                Text(
                    text = post.author,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier =
                        Modifier
                            .alignByBaseline()
                            .padding(top = 8.dp),
                )
                Spacer(modifier = Modifier.weight(1f)) // Empuja el botón a la derecha

                if (isFollowable) {
                    Button(
                        modifier =
                            Modifier
                                .alignByBaseline(),
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                contentColor = MaterialTheme.colorScheme.onBackground,
                            ),
                        onClick = {
                            if (onFollowClick != null) {
                                onFollowClick()
                            }
                        },
                    ) {
                        Text(
                            text = if (post.follow) "Seguido" else "Seguir",
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }

        Text(
            text = post.message,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
        )

        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            IconButton(
                modifier =
                    Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .size(24.dp),
                onClick = { onLikeClick() },
            ) {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = "Likes",
                    tint =
                        if (post.liked) {
                            Color.Red
                        } else {
                            MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.7f)
                        },
                )
            }
            Text(
                text = post.likes.toString(),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier =
                    Modifier
                        .width(50.dp)
                        .align(alignment = Alignment.CenterVertically)
                        .padding(end = 12.dp),
            )
            IconButton(
                modifier =
                    Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .size(23.dp),
                onClick = { onClickAction() },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat_bubble),
                    contentDescription = "Comments",
                    tint = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.7f),
                )
            }
        }
    }
}

fun getTimeInterval(postDate: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ROOT)
    // Convierte el string de postDate en formato de fecha estándar
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    // Hace que el String se interprete como una fecha en UTC
    return try {
        val date: Date? = dateFormat.parse(postDate)
        date?.let {
            val timeInMillis = it.time // Fecha actual en milisegundos
            val now = System.currentTimeMillis()
            // DateUtils hace el calculo del tiempo que paso desde la fecha dada a now
            DateUtils
                .getRelativeTimeSpanString(
                    timeInMillis,
                    now,
                    DateUtils.SECOND_IN_MILLIS,
                ).toString()
        } ?: postDate // Si el parseo de postDate devuelve null
    } catch (e: Exception) {
        e.printStackTrace()
        postDate // Devulve el valor original en caso de error
    }
}

@Preview
@Composable
fun PostViewPreview() {
    val post =
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
    PostView(post = post)
}
