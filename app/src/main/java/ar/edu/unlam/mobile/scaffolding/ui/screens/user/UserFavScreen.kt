package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.data.model.UserFavModel
import ar.edu.unlam.mobile.scaffolding.ui.components.UserFavView

@Composable
fun UserFavScreen(usersFavViewModel: UserFavViewModel = hiltViewModel()) {
    val users by usersFavViewModel.userFavState.collectAsState()

    UsersList(users)
}

@Composable
fun UsersList(users: List<UserFavModel>) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 8.dp),
    ) {
        items(users) { users -> UserFavView(userFav = users) }
        // el item tuit deveria ser clikeable para abrir el post, en una pantalla unica para verlo en detalle
    }
}
