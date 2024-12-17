package org.sopt.and.feature.login

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import org.sopt.and.data.repository.UserRepository

const val loginRoute = "loginRoute"

fun NavController.navigateToLogin(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(loginRoute) {
        builder()
    }
}

fun NavGraphBuilder.loginScreen(
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    userRepository: UserRepository
) {
    composable(route = loginRoute) {
        LoginRoute(
            onSignUpClick = onSignUpClick,
            onLoginSuccess = onLoginSuccess,
            viewModel = viewModel { LoginViewModel(userRepository) }
        )
    }
}
