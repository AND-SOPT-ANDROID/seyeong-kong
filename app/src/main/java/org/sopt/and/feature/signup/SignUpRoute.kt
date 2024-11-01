package org.sopt.and.feature.signup

import androidx.compose.foundation.background
import org.sopt.and.core.designsystem.component.textfield.WavveTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction.Companion.Next
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.sopt.and.core.common.modifier.noRippleClickable
import org.sopt.and.core.designsystem.component.button.WavveButton
import org.sopt.and.ui.theme.DarkGray2
import org.sopt.and.ui.theme.DarkGray3

@Composable
fun SignUpRoute(
    navController: NavController,
    viewModel: SignUpViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailErrorVisible by remember { mutableStateOf(false) }
    var passwordErrorVisible by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.signUpEvent) {
        viewModel.signUpEvent.collect { event ->
            when (event) {
                is SignUpEvent.Success -> {
                    navController.navigate("login") {
                        popUpTo("signup") { inclusive = true }
                    }

                }

                is SignUpEvent.Failure -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }
    }

    SignUpScreen(
        email = email,
        onEmailChange = { email = it },
        password = password,
        onPasswordChange = { password = it },
        passwordVisible = passwordVisible,
        onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
        onSignUpClick = { viewModel.onSignUpClick(email, password) },
        emailErrorVisible = emailErrorVisible,
        passwordErrorVisible = passwordErrorVisible,
        onEmailFocusChanged = { isFocused ->
            emailErrorVisible = isFocused && email.isEmpty()
        },
        onPasswordFocusChanged = { isFocused ->
            passwordErrorVisible = isFocused && password.isEmpty()
        },
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun SignUpScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    onSignUpClick: () -> Unit,
    emailErrorVisible: Boolean,
    passwordErrorVisible: Boolean,
    onEmailFocusChanged: (Boolean) -> Unit,
    onPasswordFocusChanged: (Boolean) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val signUpButtonFocusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray3)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .imePadding(),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 10.dp)
            )

            Text(
                text = "이메일과 비밀번호만으로\nWavve를 즐길 수 있어요!",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            WavveTextField(
                value = email,
                onValueChange = onEmailChange,
                placeholder = "wavve@example.com",
                errorMessage = if (emailErrorVisible) "로그인, 비밀번호 찾기, 알림에 사용되니 정확한 이메일을 입력해주세요." else null,
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
                placeholder = "Wavve 비밀번호 설정",
                isPassword = true,
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = onPasswordVisibilityChange,
                errorMessage = if (passwordErrorVisible) "비밀번호는 8~20자 이내로 영문 대소문자, 숫자, 특수문자 중 3가지 이상 혼용하여 입력해 주세요." else null,
                onFocusChanged = { isFocused ->
                    onPasswordFocusChanged(isFocused)
                },
                onNext = { signUpButtonFocusRequester.requestFocus() },
                modifier = Modifier.focusRequester(passwordFocusRequester)
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .imePadding()
                .padding(bottom = 60.dp)
        )

        WavveButton(
            text = "Wavve 회원가입",
            onClick = onSignUpClick,
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .align(Alignment.BottomCenter)
                .noRippleClickable { onSignUpClick() },
            backgroundColor = DarkGray2,
            cornerRadius = 0.dp,
            textSize = 16.sp,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}