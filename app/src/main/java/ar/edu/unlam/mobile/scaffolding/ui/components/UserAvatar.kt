package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun UserAvatar() {
    Box {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "",
            modifier =
                Modifier
                    .size(55.dp)
                    .clip(RoundedCornerShape(50)),
            alignment = Alignment.TopStart,
        )
    }
}
