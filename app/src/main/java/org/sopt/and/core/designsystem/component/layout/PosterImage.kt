package org.sopt.and.core.designsystem.component.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PosterImage(
    imageResId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    width: Dp = 140.dp,
    aspectRatio: Float = 3f / 4f
) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = contentDescription,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .width(width)
            .aspectRatio(aspectRatio),
        contentScale = ContentScale.Crop
    )
}