package ar.edu.unlam.mobile.scaffolding

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.AddPostFloatingButton
import ar.edu.unlam.mobile.scaffolding.ui.components.BottomBar
import ar.edu.unlam.mobile.scaffolding.ui.screens.feed.FeedScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.post.PostCreateScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.post.PostCreateViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.post.PostDetailScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.post.PostDraftScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.user.LoginScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.user.SignUpScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.user.UserEditScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.user.UserScreen
import ar.edu.unlam.mobile.scaffolding.ui.theme.ScaffoldingV2Theme
import dagger.hilt.android.AndroidEntryPoint

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
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Log.d("MainScreen", BuildConfig.API_KEY)

    Scaffold(
        bottomBar = {
            if (currentRoute == "feed" || currentRoute == "user") {
                BottomBar(controller)
            }
        },
        floatingActionButton = {
            if (currentRoute == "feed") {
                AddPostFloatingButton(navController = controller)
            }
        },
    ) { paddingValue ->
        // NavHost es el componente que funciona como contenedor de los otros componentes que
        // podrán ser destinos de navegación.
        NavHost(navController = controller, startDestination = "login", modifier = Modifier.padding(paddingValue)) {
            // composable es el componente que se usa para definir un destino de navegación.
            // Por parámetro recibe la ruta que se utilizará para navegar a dicho destino.
            composable("login") {
                LoginScreen(navController = controller)
            }

            composable("signUp") {
                SignUpScreen(navController = controller)
            }

            composable("feed") {
                FeedScreen(navController = controller)
            }

            composable("user") {
                UserScreen(navController = controller)
            }

            composable("postDetail/{postId}") { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId")?.toIntOrNull()
                if (postId != null) {
                    PostDetailScreen(
                        navController = controller,
                        postId = postId,
                        onBack = { controller.popBackStack() },
                    )
                }
            }

            composable("postCreation") { backStackEntry ->
                val viewModel: PostCreateViewModel = hiltViewModel()
                val draftId = backStackEntry.savedStateHandle.get<Long>("draft_id")

                LaunchedEffect(draftId) {
                    draftId?.let { viewModel.setInitialContent(it) }
                }

                PostCreateScreen(
                    navController = controller,
                )
            }

            composable("editUser") {
                UserEditScreen(navController = controller)
            }

            composable("postDrafts") {
                PostDraftScreen(navController = controller)
            }
        }
    }
}
