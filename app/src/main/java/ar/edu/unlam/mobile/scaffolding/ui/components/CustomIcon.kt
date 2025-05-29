package ar.edu.unlam.mobile.scaffolding.ui.components
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun CustomIcon(drawable: Int, colorResource: Color = colorResource(R.color.BlueSky)) {
    Icon(
        painter = painterResource(drawable),
        tint = colorResource,
        contentDescription = null,
        modifier = Modifier.padding(10.dp)
    )
}