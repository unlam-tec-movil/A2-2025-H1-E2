package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.FormValidator
import ar.edu.unlam.mobile.scaffolding.ui.components.FormField
import ar.edu.unlam.mobile.scaffolding.ui.components.PasswordFormField

@Composable
fun LogInScreen(logInViewModel: LogInViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // TITLE
        Text(
            text = stringResource(R.string.logIn),
            fontSize = 25.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
        )
// EMAIL
        FormField(
            label = stringResource(R.string.labelEmail),
            text = email,
            onTextChange = {
                email = it
                emailError = null
            },
            placeholder = stringResource(R.string.phFieldEmail),
            onFocusLost = {
                if (email.isNotBlank()) {
                    emailError =
                        FormValidator.isValidEmail(email = email)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            errorMessage = emailError,
        )

// PASSWORD
        PasswordFormField(
            label = stringResource(R.string.labelPassword),
            password = password,
            onPasswordChange = {
                password = it
                passwordError = null
            },
            placeholder = stringResource(R.string.phFieldPassword),
            errorMessage = passwordError,
            onFocusLost = {
                if (password.isNotBlank()) {
                    passwordError =
                        FormValidator.isValidText(text = password, specialCharacters = true)
                }
            },
        )

        Button(
            modifier = Modifier.padding(20.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                ),
            onClick = {
                emailError = FormValidator.isValidEmail(email = email)
                passwordError = FormValidator.isValidText(text = password, specialCharacters = true)

                if (emailError == null && passwordError == null) {
                    Log.d("AppEstado", "La app pasó por aquí correctamente.")
                    logInViewModel.loginUser(
                        email = email,
                        password = password,
                    )
                }
            },
        ) {
            Text(text = stringResource(R.string.logIn), fontSize = 20.sp)
        }
    }
}
