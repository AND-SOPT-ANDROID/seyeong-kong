package org.sopt.and.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import org.sopt.and.feature.login.LoginRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.sopt.and.core.designsystem.theme.color.ChangeStatusBarColor
import org.sopt.and.data.UserRepository
import org.sopt.and.feature.login.LoginViewModel
import org.sopt.and.feature.mypage.MyPageRoute
import org.sopt.and.feature.mypage.MyPageViewModel
import org.sopt.and.feature.signup.SignUpRoute
import org.sopt.and.feature.signup.SignUpViewModel
import org.sopt.and.ui.theme.ANDANDROIDTheme

class MainActivity : ComponentActivity() {
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        userRepository = UserRepository(this)

        setContent {
            ANDANDROIDTheme {
                ChangeStatusBarColor()

                val navController = rememberNavController()
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (userRepository.isLoggedIn()) "mypage" else "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            LoginRoute(navController = navController, viewModel = LoginViewModel(userRepository))
                        }
                        composable("mypage") {
                            MyPageRoute(navController = navController, viewModel = MyPageViewModel(userRepository))
                        }
                        composable("signup") {
                            SignUpRoute(navController = navController, viewModel = SignUpViewModel(userRepository))
                        }
                    }
                }
            }
        }
    }
}
