package ar.edu.unlam.mobile.scaffolding.ui.screens.user

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
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.FormValidator
import ar.edu.unlam.mobile.scaffolding.ui.components.FormField
import ar.edu.unlam.mobile.scaffolding.ui.components.PasswordFormField

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Título
        Text(
            text = stringResource(R.string.createAccount),
            fontSize = 25.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
        )
// NAME
        FormField(
            label = stringResource(R.string.labelName),
            text = username,
            onTextChange = {
                username = it
                usernameError = null
            },
            placeholder = stringResource(R.string.phFieldName),
            onFocusLost = {
                if (username.isNotBlank()) {
                    usernameError =
                        FormValidator.isValidText(text = username)
                }
            },
            errorMessage = usernameError,
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

// CONFIRM PASSWORD
        PasswordFormField(
            label = stringResource(R.string.labelConfirmPassword),
            password = confirmPassword,
            onPasswordChange = {
                confirmPassword = it
                confirmPasswordError = null
            },
            placeholder = stringResource(R.string.phFieldConfirmPassword),
            errorMessage = confirmPasswordError,
            onFocusLost = {
                if (password.isNotBlank()) {
                    confirmPasswordError =
                        FormValidator.isValidPassword(
                            password = password,
                            confirmPassword = confirmPassword,
                        )
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
                usernameError = FormValidator.isValidText(text = username)
                emailError = FormValidator.isValidEmail(email = email)
                passwordError = FormValidator.isValidText(text = password, specialCharacters = true)
                confirmPasswordError =
                    FormValidator.isValidPassword(
                        password = password,
                        confirmPassword = confirmPassword,
                    )
                if (usernameError == null && emailError == null && passwordError == null && confirmPasswordError == null) {
                    Log.d("AppEstado", "La app pasó por aquí correctamente.")
                    signUpViewModel.signUpUser(
                        name = username,
                        email = email,
                        password = password,
                        navController = navController,
                    )
                }
            },
        ) {
            Text(text = stringResource(R.string.SingUp), fontSize = 20.sp)
        }
    }
}
