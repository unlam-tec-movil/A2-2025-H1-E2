package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomIcon
import ar.edu.unlam.mobile.scaffolding.ui.components.PostButton
import ar.edu.unlam.mobile.scaffolding.ui.components.PostTextField
import ar.edu.unlam.mobile.scaffolding.ui.components.UserAvatar
import ar.edu.unlam.mobile.scaffolding.ui.theme.ScaffoldingV2Theme

@Composable
fun PostCreationScreen() {
    Column(modifier = Modifier.fillMaxHeight()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            CustomIcon(R.drawable.ic_close,colorResource(R.color.black),)
            PostButton(
                "Publicar", Color.White, MaterialTheme.colorScheme.primary
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            UserAvatar()
            PostTextField()
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {

            CustomIcon(R.drawable.ic_image, colorResource(R.color.BlueSky))
            CustomIcon(R.drawable.ic_gif, colorResource(R.color.BlueSky))
            CustomIcon(R.drawable.ic_qa, colorResource(R.color.BlueSky))
            CustomIcon(R.drawable.ic_addlocation, colorResource(R.color.BlueSky))
        }

    }
}


@Preview(
    name = "PostCreationPreview",
    showBackground = true
)
@Composable
fun PostCreationPreview() {
    ScaffoldingV2Theme {
        PostCreationScreen()
    }
}
