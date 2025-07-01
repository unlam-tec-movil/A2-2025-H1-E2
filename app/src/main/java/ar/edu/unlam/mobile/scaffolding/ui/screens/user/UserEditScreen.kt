package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.FormField
import ar.edu.unlam.mobile.scaffolding.ui.components.PasswordFormField
import coil.compose.AsyncImage

@Composable
fun UserEditScreen(
    userEditViewModel: UserEditViewModel = hiltViewModel(),
    navController: NavController,
) {
    val name by userEditViewModel.name.collectAsState()
    val password by userEditViewModel.password.collectAsState()
    val confirmPassword by userEditViewModel.confirmPassword.collectAsState()

    val nameError by userEditViewModel.nameError.collectAsState()
    val passwordError by userEditViewModel.passwordError.collectAsState()
    val confirmPasswordError by userEditViewModel.confirmPasswordError.collectAsState()
    val message by userEditViewModel.message.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                modifier =
                    Modifier
                        .size(300.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary),
                model = userEditViewModel.currentUserState.value.avatarURL,
                contentDescription = null,
            )
        }

        // Formulario
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // NAME
            FormField(
                label = stringResource(R.string.labelName),
                text = name,
                onTextChange = {
                    userEditViewModel.onNameChange(it)
                },
                placeholder = userEditViewModel.currentUserState.value.name,
                onFocusLost = {
                    userEditViewModel.onNameFocusLost(name)
                },
                errorMessage = nameError,
            )

            // PASSWORD
            PasswordFormField(
                label = stringResource(R.string.labelPassword),
                password = password,
                onPasswordChange = {
                    userEditViewModel.onPassWordChange(it)
                },
                placeholder = stringResource(R.string.phFieldPassword),
                errorMessage = passwordError,
                onFocusLost = {
                    userEditViewModel.onPassWordFocusLost(password)
                },
            )

            // CONFIRM PASSWORD
            PasswordFormField(
                label = stringResource(R.string.labelConfirmPassword),
                password = confirmPassword,
                onPasswordChange = {
                    userEditViewModel.onConfirmPassWordChange(it)
                },
                placeholder = stringResource(R.string.phFieldConfirmPassword),
                errorMessage = confirmPasswordError,
                onFocusLost = {
                    userEditViewModel.onConfirmPassWordFocusLost(password, confirmPassword)
                },
            )
        }
        // Botones
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                onClick = {
                    navController.navigate("user")
                },
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    "Cancelar",
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                modifier = Modifier.weight(1f),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                    ),
                onClick = {
                    if (userEditViewModel.validateDate(
                            name = name,
                            password = password,
                            confirmPassWord = confirmPassword,
                        )
                    ) {
                        userEditViewModel.editUser(name, password, navController)
                    }
                },
            ) {
                Text(
                    "Guardar cambios",
                )
            }
        }

        val context = LocalContext.current

        if (!message.isNullOrBlank()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            userEditViewModel.clearMessage()
        }
    }
}
