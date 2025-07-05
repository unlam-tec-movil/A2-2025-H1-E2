package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun AlertMessage(
    dismissRequest: () -> Unit,
    title: String,
    text: String,
    confirmButton: () -> Unit,
    cancelButton: () -> Unit,
    confirmText: String,
    cancelText: String = "",
    colorTitle: Color = MaterialTheme.colorScheme.error,
    colorText: Color = MaterialTheme.colorScheme.onSurface,
) {
    AlertDialog(
        onDismissRequest = { dismissRequest() },
        title = { Text(text = title, color = colorTitle) },
        text = { Text(text = text, fontSize = 16.sp, color = colorText) },
        confirmButton = {
            TextButton(onClick = { confirmButton() }) {
                Text(confirmText, color = colorText)
            }
        },
        dismissButton = {
            TextButton(onClick = { cancelButton() }) {
                Text(text = cancelText, color = colorText)
            }
        },
    )
}
