package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.ui.components.TuitView
import models.Tuit

@Composable
fun FeedScreen(viewModel: FeedViewModel = hiltViewModel()) {
    val tuits: List<Tuit> by viewModel.tuits.observeAsState(emptyList())

    Scaffold(
        topBar = {
            Text(
                text = "Feed",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp),
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(8.dp)
                    .padding(paddingValues = paddingValues),
        ) {
            items(tuits) { tuit ->
                TuitView(tuit = tuit)
            }
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen()
}
