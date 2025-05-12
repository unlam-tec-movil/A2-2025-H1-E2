package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun FormField(
    label: String,
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    showError: Boolean,
    isSingleLin: Boolean = true,
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text(text = label) },
        placeholder = { Text(placeholder) },
        isError = showError,
        singleLine = isSingleLin,
    )

    ErrorText(showError = showError)
}

@Composable
fun PasswordFormField(
    label: String,
    password: String,
    onPasswordChange: (String) -> Unit,
    placeholder: String,
    showError: Boolean,
    isSingleLin: Boolean = true,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(text = label) },
        placeholder = { Text(placeholder) },
        isError = showError,
        singleLine = isSingleLin,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val iconId =
                if (passwordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.clickable { passwordVisible = !passwordVisible },
            )
        },
    )

    ErrorText(showError = showError)
}

@Composable
fun ErrorText(
    showError: Boolean,
    message: String = stringResource(R.string.empty_field),
    color: Color = Color.Red,
) {
    if (showError) Text(text = message, color = color, style = MaterialTheme.typography.bodySmall)
}
