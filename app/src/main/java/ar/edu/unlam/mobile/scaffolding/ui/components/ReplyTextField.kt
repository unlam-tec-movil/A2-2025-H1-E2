package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ReplyTextField(onReply: (String) -> Unit = {}) {
    var replyText by remember { mutableStateOf("") }

    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = replyText,
            onValueChange = { replyText = it },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(9.dp),
            placeholder = { Text("Escribir respuesta") },
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = Color.White,
                ),
            onClick = {
                onReply(replyText)
                replyText = ""
            },
        ) {
            Text("Enviar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReplyTextFieldPreview() {
    ReplyTextField(onReply = {})
}
