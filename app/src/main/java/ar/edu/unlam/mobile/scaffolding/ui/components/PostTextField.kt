package ar.edu.unlam.mobile.scaffolding.ui.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun PostTextField() {

    var text by remember { mutableStateOf("you mamma...") }
    TextField(
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Red,
            cursorColor = colorResource(R.color.BlueSky),
        ),
        value = text,
        onValueChange = { text = it },
        modifier = Modifier.fillMaxWidth(),
    )

}