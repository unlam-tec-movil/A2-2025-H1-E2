package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.FormField
import ar.edu.unlam.mobile.scaffolding.ui.components.PasswordFormField

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
) {
    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val emailError by loginViewModel.emailError.collectAsState()
    val passwordError by loginViewModel.passwordError.collectAsState()
    val message by loginViewModel.message.collectAsState()

    val context = LocalContext.current

    if (!message.isNullOrBlank()) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        loginViewModel.clearMessage()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            // Título
            Text(
                text = "Iniciar sesión",
                fontSize = 25.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // EMAIL
            FormField(
                label = stringResource(R.string.labelEmail),
                text = email,
                onTextChange = {
                    loginViewModel.onEmailChange(it)
                },
                placeholder = stringResource(R.string.phFieldEmail),
                onFocusLost = {
                    loginViewModel.onEmailFocusLost(email)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                errorMessage = emailError,
//                errorMessage = emailError,
            )

            // PASSWORD
            PasswordFormField(
                label = stringResource(R.string.labelPassword),
                password = password,
                onPasswordChange = {
                    loginViewModel.onPassWordChange(it)
                },
                placeholder = stringResource(R.string.phFieldPassword),
                errorMessage = passwordError,
                onFocusLost = {
                    loginViewModel.onPassWordFocusLost(password)
                },
            )

            Button(
                modifier = Modifier.padding(20.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                    ),
                onClick = {
                    if (loginViewModel.validateDate(email = email, password = password)) {
                        loginViewModel.loginUser(
                            email = email,
                            password = password,
                            navController = navController,
                        )
                    }
                },
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 20.sp,
                )
            }
            // Registrarse
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "¿Todavía no estás registrado?",
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Button(
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                        ),
                    onClick = {
                        navController.navigate("signUp")
                    },
                ) {
                    Text(
                        text = "Registrarse",
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}
