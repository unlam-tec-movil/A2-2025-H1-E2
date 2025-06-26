package ar.edu.unlam.mobile.scaffolding.ui.screens.user

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.ui.components.UserFavView

@Composable
fun UserFavScreen(userFavViewModel: UserFavViewModel = hiltViewModel()) {
    val users by userFavViewModel.userFavState.collectAsState()
    Log.e("Cantidad actual de usuarios guardados", "es : ${users.size}")

    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
    ) {
        items(users) { userFav -> UserFavView(userFav = userFav) }
    }
}
