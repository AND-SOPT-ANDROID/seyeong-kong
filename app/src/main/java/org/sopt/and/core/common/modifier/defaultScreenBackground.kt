package org.sopt.and.core.common.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier

fun Modifier.defaultScreenBackground() = this
    .fillMaxSize()
    .background(org.sopt.and.ui.theme.DarkGray3)