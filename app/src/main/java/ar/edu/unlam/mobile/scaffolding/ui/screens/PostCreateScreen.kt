package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.PostButton
import ar.edu.unlam.mobile.scaffolding.ui.components.PostTextField
import ar.edu.unlam.mobile.scaffolding.ui.components.TopBar

@Composable
fun PostCreateScreen(
    viewModel: PostCreateViewModel = hiltViewModel(),
    navController: NavController,
) {
    val statusMessage by viewModel.statusMessage.collectAsState()

    fun back(): () -> Unit = { navController.popBackStack() }

    fun createPost(): () -> Unit =
        {
            viewModel.createPost()
            // back()
        }

    Scaffold(
        topBar = { TopBar("Nuevo Post", back()) },
        modifier = Modifier.fillMaxWidth(),
    ) {
            paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(paddingValues = paddingValues),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // TODO: Cabiar por un AsyncImage para mostrar el avatar del usuario
                    Image(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(3.dp),
                    )

                    Text(
                        text = "Usuario(Tu)",
                        // nombre del usuario logeado
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier =
                            Modifier
                                .align(alignment = Alignment.CenterVertically)
                                .padding(start = 8.dp),
                    )
                }

                PostButton(
                    text = "Publicar",
                    onTap =
                        if (viewModel.myMessage.isNotEmpty()) {
                            createPost()
                        } else {
                            {}
                        },
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    enabled = viewModel.myMessage.isNotEmpty(),
                )
            }

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
    }
}

@Preview
@Composable
fun PostCreationPreview() {
    val navController = rememberNavController()
    PostCreateScreen(navController = navController)
}
