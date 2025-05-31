package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun PostButton(
    text: String,
    textColor: Color,
    buttonColor: Color,
    onTap: () -> Unit = {},
) {
    Button(
        modifier = Modifier,
        shape = RoundedCornerShape(45),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.BlueSky.toInt())
        ),
        onClick = { onTap() },
    ) {
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
        )
    }
}

