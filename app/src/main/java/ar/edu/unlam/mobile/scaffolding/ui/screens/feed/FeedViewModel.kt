package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import models.Tuit
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
    @Inject
    constructor() : ViewModel() {
        val tuits: MutableLiveData<List<Tuit>> by lazy {
            MutableLiveData<List<Tuit>>()
        }

        init {
            // Obtener los tuits para cargar la pantalla de ini cio
            tuits.value =
                listOf(
                    Tuit(
                        1,
                        "Lalala! Que sale hoy?",
                        0,
                        "*Ayluh*",
                        "https://picsum.photos/id/237/200/300",
                        10,
                        false,
                        "2023-10-01",
                    ),
                    Tuit(
                        2,
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
                )
        }
    }
