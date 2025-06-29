package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun UserScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    navController: NavController,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Avatar
        Image(
            painter = rememberAsyncImagePainter(userViewModel.currentUserState.value.avatarURL),
            contentDescription = "User Avatar",
            modifier =
                Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Name
        Text(
//            text = userViewModel.currentUserState.value.name,
            text = "ariel",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        // Email
        Text(
//            text = userViewModel.currentUserState.value.email,
            text = "delriosariel@gmail.com",
            fontSize = 14.sp,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Options
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            OptionCard(
                title = "Editar usuario",
                icon = Icons.Default.Edit,
                onClick = { navController.navigate("editUser") },
            )
            OptionCard(
                title = "Usuarios favoritos",
                icon = Icons.Default.Favorite,
                onClick = { navController.navigate("userFavScreen") },
            )
            OptionCard(
                title = "Cerrar sesión",
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                onClick = { userViewModel.logout(navController) },
                color = Color.Red,
            )
        }
    }
}

@Composable
fun OptionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    color: Color = Color.Black,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, color = color, fontSize = 16.sp)
        }
    }
}
