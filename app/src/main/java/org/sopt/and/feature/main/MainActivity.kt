package org.sopt.and.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.sopt.and.core.designsystem.theme.color.ChangeStatusBarColor
import org.sopt.and.data.UserRepository
import org.sopt.and.feature.home.HomeRoute
import org.sopt.and.feature.login.LoginRoute
import org.sopt.and.feature.login.LoginViewModel
import org.sopt.and.feature.mypage.MyPageRoute
import org.sopt.and.feature.mypage.MyPageViewModel
import org.sopt.and.feature.search.SearchRoute
import org.sopt.and.feature.signup.SignUpRoute
import org.sopt.and.feature.signup.SignUpViewModel
import org.sopt.and.network.ServicePool.userService
import org.sopt.and.ui.theme.ANDANDROIDTheme

class MainActivity : ComponentActivity() {
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        userRepository = UserRepository(userService, applicationContext)

        setContent {
            val isLoggedIn = remember { mutableStateOf(userRepository.isLoggedIn()) }

            ANDANDROIDTheme {
                ChangeStatusBarColor()
                val navController = rememberNavController()

                navController.addOnDestinationChangedListener { _, _, _ ->
                    isLoggedIn.value = userRepository.isLoggedIn()
                }


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (isLoggedIn.value) {
                            BottomNavigationBar(
                                navController = navController,
                                modifier = Modifier.navigationBarsPadding()
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (userRepository.isLoggedIn()) Screen.Home.route else LoginRoute.route,
                        modifier = Modifier.run { padding(innerPadding) }
                    ) {
                        composable(LoginRoute.route) {
                            val viewModel = LoginViewModel(userRepository)
                            LoginRoute(navController = navController, viewModel = viewModel)
                        }
                        composable(SignUpRoute.route) {
                            val viewModel = SignUpViewModel(userRepository)
                            SignUpRoute(navController = navController, viewModel = viewModel)
                        }

                        composable(Screen.Home.route) { HomeRoute(navController) }
                        composable(Screen.Search.route) { SearchRoute(navController) }
                        composable(Screen.Profile.route) {
                            val viewModel = MyPageViewModel(userRepository)
                            MyPageRoute(navController = navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
