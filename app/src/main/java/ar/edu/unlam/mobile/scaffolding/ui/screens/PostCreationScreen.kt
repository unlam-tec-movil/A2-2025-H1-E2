package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCreationScreen(
    viewModel: PostCreationViewModel = hiltViewModel(),
    navController: NavController,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(8.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            IconButton(onClick = {
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
            }

            PostButton(
                "Publicar",
                Color.White,
                MaterialTheme.colorScheme.primary,
                onTap = { viewModel.createTuit() },
            )
        }
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            modifier =
                Modifier
                    .padding(start = 10.dp)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .padding(6.dp),
        )

        PostTextField(
            value = viewModel.description,
            onValueChange = viewModel::onDescriptionChange,
        )
    }
}

@Preview
@Composable
fun PostCreationPreview() {
    val navController = rememberNavController()
    PostCreationScreen(navController = navController)
}
