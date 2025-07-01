package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun ReplyTextField(onReply: (String) -> Unit = {}) {
    var replyText by remember { mutableStateOf("") }

        Row(
            modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = replyText,
                onValueChange = { replyText = it },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(9.dp),
                placeholder = { Text(stringResource(R.string.reply)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
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
