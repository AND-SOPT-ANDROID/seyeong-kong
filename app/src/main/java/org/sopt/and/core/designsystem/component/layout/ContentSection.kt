package org.sopt.and.core.designsystem.component.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContentSection(
    title: String,
    modifier: Modifier = Modifier,
    showMoreButton: Boolean = true,
    onMoreClick: () -> Unit = {},
    emptyContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        SectionHeader(
            title = title,
            showMoreButton = showMoreButton,
            onMoreClick = onMoreClick
        )
        Box(modifier = Modifier.padding(top = 8.dp)) {
            content()
        }
    }
}