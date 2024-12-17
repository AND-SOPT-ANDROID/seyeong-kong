package org.sopt.and.feature.mypage

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import org.sopt.and.data.repository.UserRepository

const val myPageRoute = "myPageRoute"

fun NavController.navigateToMyPage(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(myPageRoute) {
        builder()
    }
}

fun NavGraphBuilder.myPageScreen(
    onLogout: () -> Unit,
    userRepository: UserRepository
) {
    composable(route = myPageRoute) {
        MyPageRoute(
            onLogout = onLogout,
            viewModel = viewModel { MyPageViewModel(userRepository) }
        )
    }
}
