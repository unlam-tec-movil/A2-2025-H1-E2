package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.TopBar
import ar.edu.unlam.mobile.scaffolding.ui.components.UserFavView

@Composable
fun UserFavScreen(
    userFavViewModel: UserFavViewModel = hiltViewModel(),
    navController: NavController,
) {
    val users by userFavViewModel.userFavState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    if (users.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            TopBar(
                title = stringResource(R.string.tittleUserFav),
                onNavigateBack = { navController.popBackStack() },
            )
            Text(
                text = stringResource(R.string.userFvEmpty),
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    } else {
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.tittleUserFav),
                    activateDropMenu = true,
                    onNavigateBack = { navController.popBackStack() },
                    onDropMenuClick = { showDialog = true },
                    textMenu = stringResource(R.string.userFvTtMenu),
                )
                if (showDialog) {
                    AlertDialog(
                        title = {
                            Text(
                                text = stringResource(R.string.userFvDialog),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        },
                        onDismissRequest = { showDialog = false },
                        confirmButton = {
                            TextButton(onClick = {
                                userFavViewModel.deleteAllUser()
                                showDialog = false
                            }) {
                                TextColorPrimary(text = stringResource(R.string.userFvDgAccept))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialog = false }) {
                                TextColorPrimary(
                                    text = stringResource(R.string.userFvDgCancel),
                                )
                            }
                        },
                    )
                }
            },
        ) { paddingValues ->
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
            ) {
                items(users) { userFav ->
                    UserFavView(
                        userFav = userFav,
                        onDeleteClick = { userFavViewModel.deleteUser(userFav) },
                    )
                }
            }
        }
    }
}

@Composable
fun TextColorPrimary(text: String) {
    Text(text = text, color = MaterialTheme.colorScheme.onPrimary)
}
