package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.unlam.mobile.scaffolding.ui.components.TopBar

@Composable
fun PostDraftScreen(
    viewModel: PostDraftViewModel = viewModel(),
    navController: NavController,
) {
    fun back(): () -> Unit = { navController.popBackStack() }

    @Composable
    fun draftItem(
        //TODO: cambiar el tipo de dato a TuitEntity
        draft: String,
        index: Int,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable {
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("draft_index", index)

                            navController.popBackStack()
                        },
            ) {
                Text(
                    text = draft,
                    modifier = Modifier.padding(start = 16.dp),
                )
            }

            IconButton(onClick = { viewModel.deleteDraft(index) }) {
                Icon(Icons.Default.Close, contentDescription = "Eliminar")
            }
        }
    }

    Scaffold(
        topBar = { TopBar("Borradores", onNavigateBack = back()) },
        modifier = Modifier.fillMaxWidth(),
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(paddingValues = paddingValues)
                    .padding(top = 24.dp),
        ) {
            LazyColumn {
                itemsIndexed(viewModel.drafts) { index, draft ->
                    draftItem(draft, index)
                    HorizontalDivider()
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun PostDraftScreenPreview() {
    val fakeViewModel =
        PostDraftViewModel().apply {
            drafts.addAll(listOf("Borrador 1", "Borrador 2"))
        }
    PostDraftScreen(
        viewModel = fakeViewModel,
        navController = rememberNavController(),
    )
}
