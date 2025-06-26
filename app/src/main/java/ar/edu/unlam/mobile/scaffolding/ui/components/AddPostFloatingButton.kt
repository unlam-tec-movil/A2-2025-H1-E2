package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun AddPostFloatingButton(navController: NavController) {
    FloatingActionButton(
        onClick = { navController.navigate("postCreation") },
        containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.95f),
        shape = CircleShape,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Agregar",
            tint = Color.White,
        )
    }
}

@Preview
@Composable
fun AddPostFloatingButtonPreview() {
    AddPostFloatingButton(navController = NavController(LocalContext.current))
}
