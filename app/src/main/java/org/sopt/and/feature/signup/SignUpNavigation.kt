package org.sopt.and.feature.signup

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.sopt.and.data.repository.UserRepository

const val signUpRoute = "signupRoute"

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(signUpRoute, navOptions)
}

fun NavGraphBuilder.signUpScreen(
    onSignUpSuccess: () -> Unit,
    userRepository: UserRepository
) {
    composable(route = signUpRoute) {
        SignUpRoute(
            onSignUpSuccess = onSignUpSuccess,
            viewModel = viewModel { SignUpViewModel(userRepository) }
        )
    }
}
