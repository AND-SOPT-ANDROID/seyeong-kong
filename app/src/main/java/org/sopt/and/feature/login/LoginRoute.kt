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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.sopt.and.core.common.modifier.noRippleClickable
import org.sopt.and.core.designsystem.component.button.WavveButton
import org.sopt.and.core.designsystem.component.textfield.WavveTextField
import org.sopt.and.ui.theme.DarkGray3

@Composable
fun LoginRoute(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailErrorVisible by remember { mutableStateOf(false) }
    var passwordErrorVisible by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.loginEvent) {
        viewModel.loginEvent.collect { event ->
            when (event) {
                is LoginEvent.Success -> {
                    navController.navigate("mypage") {
                        popUpTo("login") { inclusive = true }
                    }
                }

                is LoginEvent.Failure -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }
    }

    LoginScreen(
        email = email,
        onEmailChange = { email = it },
        password = password,
        onPasswordChange = { password = it },
        passwordVisible = passwordVisible,
        onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
        onLoginClick = { viewModel.onLoginClick(email, password) },
        passwordErrorVisible = passwordErrorVisible,
        onEmailFocusChanged = { isFocused ->
            emailErrorVisible = isFocused && email.isEmpty()
        },
        onPasswordFocusChanged = { isFocused ->
            passwordErrorVisible = isFocused && password.isEmpty()
        },
        onNavigateToSignUp = { navController.navigate("signup") },
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun LoginScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    onLoginClick: () -> Unit,
    passwordErrorVisible: Boolean,
    onEmailFocusChanged: (Boolean) -> Unit,
    onPasswordFocusChanged: (Boolean) -> Unit,
    onNavigateToSignUp: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val emailFocusRequester = remember { FocusRequester() }
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
            value = email,
            onValueChange = onEmailChange,
            placeholder = "이메일 입력",
            onFocusChanged = { isFocused ->
                onEmailFocusChanged(isFocused)
            },
            onNext = { passwordFocusRequester.requestFocus() },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = Next),
            modifier = Modifier.focusRequester(emailFocusRequester)
        )

        Spacer(modifier = Modifier.height(16.dp))

        WavveTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = "비밀번호 입력",
            isPassword = true,
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = onPasswordVisibilityChange,
            errorMessage = if (passwordErrorVisible) "비밀번호를 입력해주세요." else null,
            onFocusChanged = { isFocused ->
                onPasswordFocusChanged(isFocused)
            },
            onNext = { loginButtonFocusRequester.requestFocus() },
            modifier = Modifier.focusRequester(passwordFocusRequester)
        )

        Text(
            text = "회원가입",
            color = Gray,
            fontWeight = Normal,
            modifier = Modifier
                .noRippleClickable {
                    onNavigateToSignUp()
                }
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .imePadding()
                .padding(bottom = 60.dp)
        )

        WavveButton(
            text = "로그인",
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .noRippleClickable { onLoginClick() },
            backgroundColor = Blue,
            cornerRadius = 12.dp,
            textSize = 16.sp,
            textStyle = TextStyle(fontWeight = SemiBold),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}