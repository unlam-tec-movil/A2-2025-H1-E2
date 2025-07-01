package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.PostButton
import ar.edu.unlam.mobile.scaffolding.ui.components.PostTextField
import ar.edu.unlam.mobile.scaffolding.ui.components.TopBar
import coil.compose.AsyncImage

@Composable
fun PostCreateScreen(
    viewModel: PostCreateViewModel = hiltViewModel(),
    navController: NavController,
) {
    val statusMessage by viewModel.statusMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val user by viewModel.currentUserState.collectAsState()

    fun refreshFeed() {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set("refresh_feed", true)
    }

    LaunchedEffect(statusMessage) {
        if (statusMessage != null && !statusMessage!!.startsWith("Error")) {
            refreshFeed()
            navController.popBackStack()
        }
    }

    fun back(): () -> Unit =
        {
            if (viewModel.myMessage.isNotEmpty()) {
                viewModel.saveDraft(viewModel.myMessage) // Guarda el borrador antes de salir
            }
            navController.popBackStack()
        }

    @Composable
    fun ButtonsView() {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(
                onClick = {
                    navController.navigate("postDrafts")
                },
                contentPadding =
                    PaddingValues(
                        horizontal = 12.dp,
                        vertical = 8.dp,
                    ),
            ) {
                Text(
                    text = stringResource(R.string.postDraft),
                    color =
                        if (viewModel.myMessage.isNotEmpty()) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.38f,
                            )
                        },
                )
            }

            PostButton(
                text = stringResource(R.string.newPost),
                onTap = { viewModel.createPost() },
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                enabled = viewModel.myMessage.isNotEmpty(),
            )
        }
    }

    @Composable
    fun UserView() {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = user.avatarURL,
                contentDescription = null,
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary),
            )

            Text(
                text = "${user.name} (Tu)",
                color = MaterialTheme.colorScheme.onSurface,
                modifier =
                    Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(start = 8.dp),
            )
        }
    }

    Scaffold(
        topBar = { TopBar(stringResource(R.string.postCreateName), back()) },
        modifier = Modifier.fillMaxWidth(),
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(paddingValues = paddingValues),
            ) {
                UserView()
                ButtonsView()
                Card(
                    shape = RoundedCornerShape(4.dp),
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .padding(bottom = 50.dp),
                ) {
                    PostTextField(
                        value = viewModel.myMessage,
                        onValueChange = viewModel::onDescriptionChange,
                    )
                }
            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

@Preview
@Composable
fun PostCreationPreview() {
    val navController = rememberNavController()
    PostCreateScreen(navController = navController)
}
