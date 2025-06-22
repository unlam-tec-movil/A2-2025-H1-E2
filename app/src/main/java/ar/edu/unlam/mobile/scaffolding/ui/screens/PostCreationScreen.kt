package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.PostButton
import ar.edu.unlam.mobile.scaffolding.ui.components.PostTextField
import ar.edu.unlam.mobile.scaffolding.ui.components.topBar

@Composable
fun PostCreationScreen(
    viewModel: PostCreationViewModel = hiltViewModel(),
    navController: NavController,
) {

    fun back(): () -> Unit = {navController.popBackStack()}

    Scaffold(
        topBar = { topBar("Nuevo Post", back()) },
        modifier = Modifier.fillMaxWidth(),
    )
    {
        paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues = paddingValues),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    /*IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = Color.Black,
                            contentDescription = null,
                            modifier =
                                Modifier
                                    .size(32.dp),
                        )
                    }*/
                    Image(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(3.dp),
                    )
                    Text(
                        text = "UserName",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding(start = 8.dp)
                    )

                }

                PostButton(
                    text = "Publicar",
                    Color.White,
                    MaterialTheme.colorScheme.primary,
                    onTap = { viewModel.createTuit() },
                    end = Arrangement.End
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )

            PostTextField(
                value = viewModel.description,
                onValueChange = viewModel::onDescriptionChange,

            )
        }
    }
}

@Preview
@Composable
fun PostCreationPreview() {
    val navController = rememberNavController()
    PostCreationScreen(navController = navController)
}
