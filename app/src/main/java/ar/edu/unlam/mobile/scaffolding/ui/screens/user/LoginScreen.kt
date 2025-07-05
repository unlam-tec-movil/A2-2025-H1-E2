package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.AlertMessage
import ar.edu.unlam.mobile.scaffolding.ui.components.ButtonDesign
import ar.edu.unlam.mobile.scaffolding.ui.components.FormField
import ar.edu.unlam.mobile.scaffolding.ui.components.PasswordFormField
import ar.edu.unlam.mobile.scaffolding.ui.components.TitleText

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by loginViewModel.state.collectAsState()
    val validation by loginViewModel.validation
    val showDialog by loginViewModel.showErrorDialog

    if (showDialog != null) {
        AlertMessage(
            dismissRequest = { (loginViewModel.dismissRequest()) },
            title = stringResource(R.string.titleDialog),
            text = showDialog!!,
            confirmButton = { (loginViewModel.dismissRequest()) },
            confirmText = stringResource(R.string.acceptDialog),
            cancelButton = {},
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            // Título
            TitleText(stringResource(R.string.logIn))

            Spacer(modifier = Modifier.height(16.dp))

            // EMAIL
            FormField(
                label = stringResource(R.string.labelEmail),
                text = state.email,
                onTextChange = { loginViewModel.onEmailChanged(it) },
                placeholder = stringResource(R.string.phFieldEmail),
                onFocusLost = { loginViewModel.onEmailFocusLost(state.email) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                errorMessage = state.emailError,
            )

            // PASSWORD
            PasswordFormField(
                label = stringResource(R.string.labelPassword),
                password = state.password,
                onPasswordChange = { loginViewModel.onPasswordChanged(it) },
                placeholder = stringResource(R.string.phFieldPassword),
                errorMessage = state.passwordError,
                onFocusLost = { loginViewModel.onPasswordFocusLost(state.password) },
            )

            ButtonDesign(
                text = stringResource(R.string.loginButton),
                onClickButton = {
                    loginViewModel.submitData()
                    if (validation) {
                        loginViewModel.loginUser(state.email, state.password, navController)
                    }
                },
            )

            // Registrarse
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.notRegistered),
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                ButtonDesign(
                    text = stringResource(R.string.singUpButton),
                    onClickButton = { navController.navigate("signUp") },
                )
            }
        }
    }
}
