package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController,
) {
    val name by signUpViewModel.name.collectAsState()
    val email by signUpViewModel.email.collectAsState()
    val password by signUpViewModel.password.collectAsState()
    val confirmPassword by signUpViewModel.confirmPassword.collectAsState()

    val nameError by signUpViewModel.nameError.collectAsState()
    val emailError by signUpViewModel.emailError.collectAsState()
    val passwordError by signUpViewModel.passwordError.collectAsState()
    val confirmPasswordError by signUpViewModel.confirmPasswordError.collectAsState()
    val message by signUpViewModel.message.collectAsState()

    val context = LocalContext.current

    if (!message.isNullOrBlank()) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        signUpViewModel.clearMessage()
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        // Título
        Text(
            text = stringResource(R.string.createAccount),
            fontSize = 25.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary,
        )

        Column {
            // NAME
            FormField(
                label = stringResource(R.string.labelName),
                text = name,
                onTextChange = {
                    signUpViewModel.onNameChange(it)
                },
                placeholder = stringResource(R.string.phFieldName),
                onFocusLost = {
                    signUpViewModel.onNameFocusLost(name)
                },
                errorMessage = nameError,
            )

            // EMAIL
            FormField(
                label = stringResource(R.string.labelEmail),
                text = email,
                onTextChange = {
                    signUpViewModel.onEmailChange(it)
                },
                placeholder = stringResource(R.string.phFieldEmail),
                onFocusLost = {
                    signUpViewModel.onEmailFocusLost(email)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                errorMessage = emailError,
            )

            // PASSWORD
            PasswordFormField(
                label = stringResource(R.string.labelPassword),
                password = password,
                onPasswordChange = {
                    signUpViewModel.onPassWordChange(it)
                },
                placeholder = stringResource(R.string.phFieldPassword),
                errorMessage = passwordError,
                onFocusLost = {
                    signUpViewModel.onPassWordFocusLost(password)
                },
            )

            // CONFIRM PASSWORD
            PasswordFormField(
                label = stringResource(R.string.labelConfirmPassword),
                password = confirmPassword,
                onPasswordChange = {
                    signUpViewModel.onConfirmPassWordChange(it)
                },
                placeholder = stringResource(R.string.phFieldConfirmPassword),
                errorMessage = confirmPasswordError,
                onFocusLost = {
                    signUpViewModel.onConfirmPassWordFocusLost(password, confirmPassword)
                },
            )
        }

        Button(
            modifier = Modifier.padding(20.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                ),
            onClick = {
                if (signUpViewModel.validateDate(name, email, password, confirmPassword)) {
                    signUpViewModel.signUpUser(
                        name = name,
                        email = email,
                        password = password,
                        navController = navController,
                    )
                }
            },
        ) {
            Text(
                text = stringResource(R.string.singUp),
                fontSize = 20.sp,
            )
        }
    }
}
