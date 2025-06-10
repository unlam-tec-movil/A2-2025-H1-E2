package ar.edu.unlam.mobile.scaffolding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.unlam.mobile.scaffolding.ui.components.BottomBar
import ar.edu.unlam.mobile.scaffolding.ui.screens.FeedScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.UserScreen
import ar.edu.unlam.mobile.scaffolding.ui.theme.ScaffoldingV2Theme
import dagger.hilt.android.AndroidEntryPoint
import models.Tuit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldingV2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    // Controller es el elemento que nos permite navegar entre pantallas. Tiene las acciones
    // para navegar como naviegate y también la información de en dónde se "encuentra" el usuario
    // a través del back stack
    val controller = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(controller = controller) },
        floatingActionButton = {
            IconButton(onClick = { controller.navigate("feed") }) {
                Icon(Icons.Filled.Home, contentDescription = "Feed")
            }
        },
    ) { paddingValue ->
        // NavHost es el componente que funciona como contenedor de los otros componentes que
        // podrán ser destinos de navegación.
        NavHost(navController = controller, startDestination = "feed", modifier = Modifier.padding(paddingValue)) {
            // composable es el componente que se usa para definir un destino de navegación.
            // Por parámetro recibe la ruta que se utilizará para navegar a dicho destino.
            composable("feed") {
                FeedScreen(
                    tuits =
                        listOf(
                            Tuit(1, "Lalala! Que sale hoy?", 0, "*Ayluh*", "https://picsum.photos/id/237/200/300", 10, false, "2023-10-01"),
                            Tuit(2,
                                "Aguanten los michis y el helado de limon",
                                0,
                                "Andy~.",
                                "https://i.pinimg.com/736x/40/62/fe/4062fe8c64ff8b9a581ae2669a70766c.jpg",
                                5,
                                true,
                                "2023-10-02",
                            ),
                            Tuit(
                                3,
                                "From Tim McGraw to New Year’s Day 💚💛💜❤️🩵🖤",
                                0,
                                "Tau⸆⸉⛅",
                                "https://www.rionegro.com.ar/wp-content/uploads/2023/11/taylor-swift.jpg?w=375&h=211&crop=1",
                                20,
                                false,
                                "2023-10-03",
                            ),
                        ),
                )
            }
            composable(
                route = "user/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("id") ?: "1"
                UserScreen(userId = id)
            }
        }
    }
}
