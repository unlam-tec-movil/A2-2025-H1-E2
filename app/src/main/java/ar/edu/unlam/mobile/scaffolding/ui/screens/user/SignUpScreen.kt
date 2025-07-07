package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.AlertMessage
import ar.edu.unlam.mobile.scaffolding.ui.components.ButtonDesign
import ar.edu.unlam.mobile.scaffolding.ui.components.FormField
import ar.edu.unlam.mobile.scaffolding.ui.components.PasswordFormField
import ar.edu.unlam.mobile.scaffolding.ui.components.TitleText

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by signUpViewModel.state.collectAsState()
    val showDialog by signUpViewModel.showErrorDialog

    if (showDialog != null) {
        AlertMessage(
            dismissRequest = { (signUpViewModel.dismissRequest()) },
            title = stringResource(R.string.titleDialog),
            text = showDialog!!,
            confirmButton = { (signUpViewModel.dismissRequest()) },
            confirmText = stringResource(R.string.acceptDialog),
            cancelButton = {},
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        // Título
        TitleText(
            text = stringResource(R.string.createAccount),
        )

        Column {
            // NAME
            FormField(
                label = stringResource(R.string.labelName),
                text = state.name,
                onTextChange = { signUpViewModel.onNameChanged(it) },
                placeholder = stringResource(R.string.phFieldName),
                onFocusLost = { signUpViewModel.onNameFocusLost(state.name) },
                errorMessage = state.nameError,
            )

            // EMAIL
            FormField(
                label = stringResource(R.string.labelEmail),
                text = state.email,
                onTextChange = { signUpViewModel.onEmailChanged(it) },
                placeholder = stringResource(R.string.phFieldEmail),
                onFocusLost = { signUpViewModel.onEmailFocusLost(state.email) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                errorMessage = state.emailError,
            )

            // PASSWORD
            PasswordFormField(
                label = stringResource(R.string.labelPassword),
                password = state.password,
                onPasswordChange = { signUpViewModel.onPassWordChanged(it) },
                placeholder = stringResource(R.string.phFieldPassword),
                errorMessage = state.passwordError,
                onFocusLost = { signUpViewModel.onPasswordFocusLost(state.password) },
            )

            // CONFIRM PASSWORD
            PasswordFormField(
                label = stringResource(R.string.labelConfirmPassword),
                password = state.confirmPassword,
                onPasswordChange = { signUpViewModel.onConfirmPasswordChanged(it) },
                placeholder = stringResource(R.string.phFieldConfirmPassword),
                errorMessage = state.confirmPasswordError,
                onFocusLost = { signUpViewModel.onConfirmPasswordFocusLost(state.confirmPassword) },
            )
        }

        ButtonDesign(
            text = stringResource(R.string.singUp),
            onClickButton = {
                if (signUpViewModel.submitData()) {
                    signUpViewModel.signUpUser(
                        name = state.name,
                        email = state.email,
                        password = state.password,
                        navController = navController,
                    )
                }
            },
        )
    }
}
