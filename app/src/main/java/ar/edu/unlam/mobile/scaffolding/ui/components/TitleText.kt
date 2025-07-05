package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(
    text: String,
    fontSize: Int = 25,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    fontStyle: FontStyle = FontStyle.Normal,
    fontWeight: FontWeight = FontWeight.SemiBold,
) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        color = color,
    )
}
