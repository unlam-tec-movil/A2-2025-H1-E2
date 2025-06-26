package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomBar(controller: NavHostController) {
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    Column {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            thickness = 1.dp,
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            NavigationBarItem(
                selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "feed" } == true,
                onClick = { controller.navigate("feed") },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Feed",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                },
            )
            NavigationBarItem(
                selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "user" } == true,
                onClick = { controller.navigate("user") },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Usuario",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                },
            )
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    val mockNavController = rememberNavController()
    BottomBar(controller = mockNavController)
}
