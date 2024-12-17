package org.sopt.and.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.sopt.and.core.designsystem.theme.color.ChangeStatusBarColor
import org.sopt.and.data.datasourceImpl.UserDataLocalSourceImpl
import org.sopt.and.data.datasourceImpl.UserRemoteDataSourceImpl
import org.sopt.and.data.repository.UserRepository
import org.sopt.and.domain.repository.UserRepositoryImpl
import org.sopt.and.network.ServicePool.userService
import org.sopt.and.ui.theme.ANDANDROIDTheme

class MainActivity : ComponentActivity() {
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initializeRepository()

        setContent {
            ANDANDROIDTheme {
                ChangeStatusBarColor()
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val showBottomBar = navBackStackEntry?.destination?.parent?.route == NavigationRoute.Main.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavigationBar(
                                navController = navController,
                                modifier = Modifier.navigationBarsPadding()
                            )
                        }
                    }
                ) { innerPadding ->
                    WavveNavHost(
                        navController = navController,
                        startDestination = if (userRepository.isLoggedIn()) {
                            NavigationRoute.Main.route
                        } else {
                            NavigationRoute.Auth.route
                        },
                        userRepository = userRepository,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun initializeRepository() {
        val sharedPreferences = applicationContext.getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userLocalDataSource = UserDataLocalSourceImpl(sharedPreferences)
        val userRemoteDataSource = UserRemoteDataSourceImpl(userService)

        userRepository = UserRepositoryImpl(
            userRemoteDataSource = userRemoteDataSource,
            userLocalDataSource = userLocalDataSource
        )
    }
}
