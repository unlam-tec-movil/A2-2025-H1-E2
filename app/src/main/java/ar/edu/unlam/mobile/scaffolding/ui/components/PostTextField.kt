package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun PostTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Red,
                cursorColor = colorResource(R.color.BlueSky),
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
            ),
        value = value,
        onValueChange = onValueChange,
        singleLine = false,
        placeholder = { Text("Escribe algo...") },
        modifier = Modifier.fillMaxWidth(),
    )
}
