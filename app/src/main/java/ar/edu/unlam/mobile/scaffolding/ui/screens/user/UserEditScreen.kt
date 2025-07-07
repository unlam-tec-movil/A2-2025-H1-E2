package ar.edu.unlam.mobile.scaffolding.ui.screens.user
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.ButtonDesign
import ar.edu.unlam.mobile.scaffolding.ui.components.FormField
import ar.edu.unlam.mobile.scaffolding.ui.components.PasswordFormField
import coil.compose.AsyncImage

@Composable
fun UserEditScreen(
    userEditViewModel: UserEditViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by userEditViewModel.state.collectAsState()

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
            AsyncImage(
                modifier =
                    Modifier
                        .size(150.dp)
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
                text = state.name,
                onTextChange = { userEditViewModel.onNameChanged(it) },
                placeholder = userEditViewModel.currentUserState.value.name,
                onFocusLost = { userEditViewModel.onNameFocusLost(state.name) },
                errorMessage = state.nameError,
            )

            // PASSWORD
            PasswordFormField(
                label = stringResource(R.string.labelPassword),
                password = state.password,
                onPasswordChange = { userEditViewModel.onPasswordChanged(it) },
                placeholder = stringResource(R.string.phFieldPassword),
                errorMessage = state.passwordError,
                onFocusLost = { userEditViewModel.onPasswordFocusLost(state.password) },
            )

            // CONFIRM PASSWORD
            PasswordFormField(
                label = stringResource(R.string.labelConfirmPassword),
                password = state.confirmPassword,
                onPasswordChange = { userEditViewModel.onConfirmPasswordChanged(it) },
                placeholder = stringResource(R.string.phFieldConfirmPassword),
                errorMessage = state.confirmPasswordError,
                onFocusLost = {
                    userEditViewModel.onConfirmPasswordFocusLost(
                        state.confirmPassword,
                    )
                },
            )
        }
        // Botones
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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

            ButtonDesign(
                text = "Guardar cambios",
                fontSize = 17,
                onClickButton = {
                    if (userEditViewModel.submitData()) {
                        userEditViewModel.editUser(state.name, state.password, navController)
                    }
                },
            )
        }
    }
}
