package org.sopt.and.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.core.common.modifier.noRippleClickable
import org.sopt.and.ui.theme.DarkGray2

@Composable
fun WavveTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityChange: (() -> Unit)? = null,
    errorMessage: String? = null,
    onFocusChanged: (Boolean) -> Unit,
    onNext: () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier
) {
    var isPasswordVisible by remember { mutableStateOf(passwordVisible) }

    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(DarkGray2)
                .height(64.dp)
                .padding(horizontal = 12.dp, vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    ),
                    visualTransformation = if (isPassword && !isPasswordVisible)
                        PasswordVisualTransformation() else VisualTransformation.None,
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged { focusState ->
                            onFocusChanged(focusState.isFocused)
                        },
                    keyboardOptions = keyboardOptions,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            onNext()
                        }
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(Color.LightGray),
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Color.LightGray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                if (isPassword && onPasswordVisibilityChange != null) {
                    Text(
                        text = if (isPasswordVisible) "hide" else "show",
                        fontWeight = Medium,
                        color = Color.White,
                        modifier = Modifier
                            .noRippleClickable {
                                isPasswordVisible = !isPasswordVisible
                                onPasswordVisibilityChange()
                            }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp
            )
        }
    }
}