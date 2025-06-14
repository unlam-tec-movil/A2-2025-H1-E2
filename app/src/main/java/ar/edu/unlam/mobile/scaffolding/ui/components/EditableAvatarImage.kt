package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun EditableAvatarImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String?,
    onEditClick: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .size(128.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier =
                Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop,
        )
        IconButton(
            onClick = onEditClick,
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f), CircleShape)
                    .padding(4.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Editar imagen",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
