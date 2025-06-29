package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun FormField(
    label: String,
    // etiqueta del formulario
    text: String,
    // valor dado por el usario
    onTextChange: (String) -> Unit,
    // actualiza el valor cuando el usario escribe
    placeholder: String,
    // texto que se muestra cuando el campo esta vacio
    errorMessage: String?,
    // mensaje de error que se mostrara debajo del campo
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    // opciones para el teclado
    isSingleLine: Boolean = true,
    onFocusLost: () -> Unit,
    // esto se ejecuta cuando el formulario no esta en foco
) {
    val showError = errorMessage != null

    TextField(
        modifier =
            Modifier
                .padding(24.dp)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        onFocusLost()
                    }
                },
        value = text,
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                // fondo cuando el formulario está enfocado
                unfocusedContainerColor = Color.Transparent,
                // fondo cuando el formulario no está enfocado
            ),
        onValueChange = onTextChange,
        label = { Text(text = label) },
        placeholder = { Text(placeholder) },
        keyboardOptions = keyboardOptions,
        isError = showError,
        singleLine = isSingleLine,
        supportingText = { ErrorText(showError = showError, message = errorMessage) },
    )
}

@Composable
fun PasswordFormField(
    label: String,
    password: String,
    onPasswordChange: (String) -> Unit,
    placeholder: String,
    errorMessage: String?,
    isSingleLin: Boolean = true,
    onFocusLost: () -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val showError = errorMessage != null

    TextField(
        modifier =
            Modifier
                .padding(24.dp)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        onFocusLost()
                    }
                },
        value = password,
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                // fondo cuando el formulario está enfocado
                unfocusedContainerColor = Color.Transparent,
                // fondo cuando el formulario no está enfocado
            ),
        onValueChange = onPasswordChange,
        label = { Text(text = label) },
        placeholder = { Text(placeholder) },
        isError = showError,
        singleLine = isSingleLin,
        supportingText = { ErrorText(showError = showError, message = errorMessage) },
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
}

// esta funcion muestra el error el cual es recibido por parametro por el formulario y lo muestra
@Composable
fun ErrorText(
    showError: Boolean,
    message: String?,
) {
    if (showError) {
        Text(
            text = message ?: "",
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
