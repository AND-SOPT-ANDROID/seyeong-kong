package org.sopt.and.core.designsystem.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WavveButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Blue,
    contentColor: Color = Color.White,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 1.dp,
    cornerRadius: Dp = 8.dp,
    textSize: TextUnit = 16.sp,
    textStyle: TextStyle = TextStyle.Default,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(cornerRadius),
        border = BorderStroke(width = borderWidth, color = borderColor),
        contentPadding = contentPadding
    ) {
        Text(
            text = text,
            style = textStyle.copy(fontSize = textSize)
        )
    }
}