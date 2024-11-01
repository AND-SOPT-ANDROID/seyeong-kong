package org.sopt.and.core.designsystem.component.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun HorizontalPosterRow(
    modifier: Modifier = Modifier,
    items: List<Int>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp)
) {
    HorizontalContentRow(
        modifier = modifier,
        items = items,
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement
    ) { imageResId ->
        PosterImage(
            imageResId = imageResId,
            contentDescription = "Poster Image"
        )
    }
}