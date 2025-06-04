package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.ui.components.TuitView
import models.Tuit

@Composable
fun FeedScreen(tuits: List<Tuit>) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(8.dp),
    ) {
        items(tuits) { tuit ->
            TuitView(tuit = tuit)
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    val sampleTuits =
        listOf(
            Tuit(1, "Lalala! Que sale hoy?", 0, "*Ayluh*", "https://example.com/avatar1.png", 10, false, "2023-10-01"),
            Tuit(2, "Aguanten los michis y el helado de limon", 0, "Andy~.", "https://example.com/avatar2.png", 5, true, "2023-10-02"),
            Tuit(
                3,
                "From Tim McGraw to New Year’s Day 💚💛💜❤️🩵🖤",
                0,
                "Tau⸆⸉⛅",
                "https://example.com/avatar3.png",
                20,
                false,
                "2023-10-03",
            ),
        )
    FeedScreen(tuits = sampleTuits)
}
