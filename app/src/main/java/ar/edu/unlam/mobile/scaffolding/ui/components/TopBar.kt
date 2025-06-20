package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBar(title: String, /*onNavigateBack: () -> Unit*/) {
    TopAppBar(
        title = { Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )},
        navigationIcon = {
            /*IconButton(onClick = onNavigateBack) { // La idea es que al hacer clic en el botón,
             // se retroceda a la pantalla anterior llamando al navController con la lambda
             //el boton solo dedberia aparecer al abrir un post
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Retroceder"
                    modifier = Modifier
                       .padding(horizontal = 8.dp)
                )
            }*/
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}


@Preview
@Composable
fun topBarPreview(){
    topBar("Feed")
}