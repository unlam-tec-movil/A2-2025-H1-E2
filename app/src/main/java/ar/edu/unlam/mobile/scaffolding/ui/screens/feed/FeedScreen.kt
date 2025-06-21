package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.ui.components.TuitView
import ar.edu.unlam.mobile.scaffolding.ui.components.topBar
import models.Tuit

@Composable
fun FeedScreen(feedViewModel: FeedViewModel = hiltViewModel()) {

    val posts by feedViewModel.feedState.observeAsState(emptyList())
    Log.d("Cantidad actual de posts:", "${posts.size}")

    Scaffold(
        topBar = { topBar("Feed") },
    ) {
        paddingValues ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 8.dp)
                    .padding(paddingValues = paddingValues),
        ) {
            items(posts) { tuit -> TuitView(tuit = tuit) }
        //el item tuit deveria ser clikeable para abrir el post, en una pantalla unica para verlo en detalle
            }
        }
    }


@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen()
}
