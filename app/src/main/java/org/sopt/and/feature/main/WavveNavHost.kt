package org.sopt.and.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import org.sopt.and.data.repository.UserRepository
import org.sopt.and.feature.home.homeRoute
import org.sopt.and.feature.home.homeScreen
import org.sopt.and.feature.home.navigateToHome
import org.sopt.and.feature.login.loginRoute
import org.sopt.and.feature.login.loginScreen
import org.sopt.and.feature.login.navigateToLogin
import org.sopt.and.feature.mypage.myPageScreen
import org.sopt.and.feature.mypage.navigateToMyPage
import org.sopt.and.feature.search.searchScreen
import org.sopt.and.feature.signup.navigateToSignUp
import org.sopt.and.feature.signup.signUpScreen

@Composable
fun WavveNavHost(
    navController: NavHostController,
    startDestination: String,
    userRepository: UserRepository,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Auth Graph
        navigation(
            route = NavigationRoute.Auth.route,
            startDestination = loginRoute
        ) {
            loginScreen(
                onSignUpClick = {
                    navController.navigateToSignUp()
                },
                onLoginSuccess = {
                    navController.navigateToHome()
                },
                userRepository = userRepository
            )
            
            signUpScreen(
                onSignUpSuccess = {
                    navController.navigateToLogin {
                        popUpTo(NavigationRoute.Auth.route) {
                            inclusive = true
                        }
                    }
                },
                userRepository = userRepository
            )
        }

        // Main Graph
        navigation(
            route = NavigationRoute.Main.route,
            startDestination = homeRoute
        ) {
            homeScreen(
                onNavigateToDetail = { /* navigation */ }
            )
            searchScreen()
            myPageScreen(
                onLogout = {
                    navController.navigateToMyPage {
                        popUpTo(NavigationRoute.Main.route) {
                            inclusive = true
                        }
                    }
                },
                userRepository = userRepository
            )
        }
    }
}

sealed class NavigationRoute(val route: String) {
    data object Auth : NavigationRoute("auth_graph")
    data object Main : NavigationRoute("main_graph")
}
