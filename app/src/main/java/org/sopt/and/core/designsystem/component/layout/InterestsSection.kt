package org.sopt.and.core.designsystem.component.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InterestsSection(
    title: String,
    emptyMessage: String,
    interests: List<Int> = emptyList(),
    showMoreButton: Boolean = true,
    onMoreClick: () -> Unit = {}
) {
    ContentSection(
        title = title,
        showMoreButton = showMoreButton,
        onMoreClick = onMoreClick
    ) {
        if (interests.isEmpty()) {
            EmptyContent(
                message = emptyMessage,
                modifier = Modifier.padding(vertical = 48.dp)
            )
        } else {
            HorizontalPosterRow(
                items = interests,
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            )
        }
    }
}