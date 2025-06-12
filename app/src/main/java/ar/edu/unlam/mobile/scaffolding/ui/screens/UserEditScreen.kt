package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.FormValidator
import ar.edu.unlam.mobile.scaffolding.ui.components.EditableCircularImage
import ar.edu.unlam.mobile.scaffolding.ui.components.FormField
import ar.edu.unlam.mobile.scaffolding.ui.components.PasswordFormField

@Composable
fun UserEditScreen(userEditViewModel: UserEditViewModel = hiltViewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            EditableCircularImage(
                imageUrl = userEditViewModel.currentUser.value.avatarURL,
                contentDescription = null,
                onEditClick = {
                    // Todo
                },
            )
        }

        // Formulario
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
        ) {
            // NAME
            FormField(
                label = stringResource(R.string.labelName),
                text = username,
                onTextChange = {
                    username = it
                    usernameError = null
                },
                placeholder = userEditViewModel.currentUser.value.name,
                onFocusLost = {
                    if (username.isNotBlank()) {
                        usernameError =
                            FormValidator.isValidText(text = username)
                    }
                },
                errorMessage = usernameError,
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
        }
        // Botones
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                onClick = {
                    // Todo: Back navigation
                },
                modifier = Modifier.weight(1f),
            ) {
                Text("cancelar")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    usernameError = FormValidator.isValidText(text = username)
                    passwordError = FormValidator.isValidText(text = password, specialCharacters = true)
                    confirmPasswordError =
                        FormValidator.isValidPassword(
                            password = password,
                            confirmPassword = confirmPassword,
                        )
                    if (usernameError == null && passwordError == null && confirmPasswordError == null) {
                        Log.d("AppEstado", "La app pasó por aquí correctamente.")
                        userEditViewModel.editUser(
                            name = username,
                            password = password,
                        )
                    }
                },
            ) {
                Text("guardar cambios")
            }
        }
    }
}
