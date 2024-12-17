package org.sopt.and.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.ImeAction.Companion.Next
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.sopt.and.core.common.modifier.noRippleClickable
import org.sopt.and.core.designsystem.component.button.WavveButton
import org.sopt.and.core.designsystem.component.textfield.WavveTextField
import org.sopt.and.ui.theme.DarkGray3

@Composable
fun LoginRoute(
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.loginEvent) {
        uiState.loginEvent?.let { event ->
            when (event) {
                is LoginEvent.Success -> {
                    onLoginSuccess()
                }
                is LoginEvent.Failure -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
            viewModel.handleIntent(LoginIntent.ConsumeLoginEvent)
        }
    }

    LoginScreen(
        state = uiState,
        onIntent = viewModel::handleIntent,
        onSignUpClick = onSignUpClick,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit,
    onSignUpClick: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val usernameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val loginButtonFocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray3)
            .imePadding()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.statusBarsPadding())

        Text(
            text = "Wavve",
            fontWeight = Bold,
            color = Gray,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        WavveTextField(
            value = state.username,
            onValueChange = { onIntent(LoginIntent.UpdateUsername(it)) },
            placeholder = "사용자 이름 입력",
            onFocusChanged = { isFocused ->
                onIntent(LoginIntent.UpdateUsernameFocusState(isFocused))
            },
            onNext = { passwordFocusRequester.requestFocus() },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = Next),
            modifier = Modifier.focusRequester(usernameFocusRequester)
        )

        Spacer(modifier = Modifier.height(16.dp))

        WavveTextField(
            value = state.password,
            onValueChange = { onIntent(LoginIntent.UpdatePassword(it)) },
            placeholder = "비밀번호 입력",
            isPassword = true,
            passwordVisible = state.passwordVisible,
            onPasswordVisibilityChange = { onIntent(LoginIntent.TogglePasswordVisibility) },
            errorMessage = if (state.isPasswordErrorVisible) "비밀번호를 입력해주세요." else null,
            onFocusChanged = { isFocused ->
                onIntent(LoginIntent.UpdatePasswordFocusState(isFocused))
            },
            onNext = { loginButtonFocusRequester.requestFocus() },
            modifier = Modifier.focusRequester(passwordFocusRequester)
        )

        Text(
            text = "회원가입",
            color = Gray,
            fontWeight = Normal,
            modifier = Modifier
                .noRippleClickable(onSignUpClick)
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .imePadding()
                .padding(bottom = 60.dp)
        )

        WavveButton(
            text = "로그인",
            onClick = { onIntent(LoginIntent.Login) },
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .focusRequester(loginButtonFocusRequester),
            backgroundColor = Blue,
            cornerRadius = 12.dp,
            textSize = 16.sp,
            textStyle = TextStyle(fontWeight = SemiBold),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}
