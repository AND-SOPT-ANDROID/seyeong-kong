package org.sopt.and.core.designsystem.theme.color

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ChangeStatusBarColor() {
    val systemUiController = rememberSystemUiController()

    val statusBarColor = Color(android.graphics.Color.BLACK)

    systemUiController.setStatusBarColor(
        color = statusBarColor,
        darkIcons = false
    )
}
