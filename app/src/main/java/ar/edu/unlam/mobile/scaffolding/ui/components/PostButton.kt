package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PostButton(
    text: String,
    enabled: Boolean,
    onTap: () -> Unit = {},
    contentPadding: PaddingValues,
) {
    Button(
        onClick = { onTap() },
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f),
                disabledContentColor = Color.Gray
            ),
        contentPadding = contentPadding,
    ) {
        Text(
            text = text,
            modifier =
                Modifier
                    .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
        )
    }
}

@Preview
@Composable
fun PostButtonPreview() {
    PostButton(
        text = "Publicar",
        enabled = true,
        onTap = {},
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
    )
}
