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
import kotlinx.serialization.Serializable
import org.sopt.and.core.common.modifier.noRippleClickable
import org.sopt.and.core.designsystem.component.button.WavveButton
import org.sopt.and.feature.login.LoginRoute
import org.sopt.and.ui.theme.DarkGray2
import org.sopt.and.ui.theme.DarkGray3

@Serializable
data object SignUpRoute {
    const val route = "signup"
}

@Composable
fun SignUpRoute(
    navController: NavController,
    viewModel: SignUpViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hobby by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.signUpEvent) {
        viewModel.signUpEvent.collect { event ->
            when (event) {
                is SignUpEvent.Success -> {
                    navController.navigate(LoginRoute.route) {
                        popUpTo(SignUpRoute.route) { inclusive = true }
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
        username = username,
        onUsernameChange = { username = it },
        password = password,
        onPasswordChange = { password = it },
        hobby = hobby,
        onHobbyChange = { hobby = it },
        passwordVisible = isPasswordVisible,
        onPasswordVisibilityChange = { isPasswordVisible = !isPasswordVisible },
        onSignUpClick = { viewModel.onSignUpClick(username, password, hobby) },
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun SignUpScreen(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    hobby: String,
    onHobbyChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    onSignUpClick: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val usernameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val hobbyFocusRequester = remember { FocusRequester() }

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
                text = "사용자 이름, 비밀번호, 취미를 입력하여\nWavve를 즐길 수 있어요!",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            WavveTextField(
                value = username,
                onValueChange = onUsernameChange,
                placeholder = "사용자 이름",
                onFocusChanged = { /* 사용자 이름 필드의 포커스 변화 처리 */ },
                onNext = { passwordFocusRequester.requestFocus() },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = Next),
                modifier = Modifier.focusRequester(usernameFocusRequester)
            )

            Spacer(modifier = Modifier.height(16.dp))

            WavveTextField(
                value = password,
                onValueChange = onPasswordChange,
                placeholder = "비밀번호 설정",
                isPassword = true,
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = onPasswordVisibilityChange,
                onFocusChanged = { /* 비밀번호 필드의 포커스 변화 처리 */ },
                onNext = { hobbyFocusRequester.requestFocus() },
                modifier = Modifier.focusRequester(passwordFocusRequester)
            )

            Spacer(modifier = Modifier.height(16.dp))

            WavveTextField(
                value = hobby,
                onValueChange = onHobbyChange,
                placeholder = "취미",
                onFocusChanged = { /* 취미 필드의 포커스 변화 처리 */ },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = Next),
                modifier = Modifier.focusRequester(hobbyFocusRequester)
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
