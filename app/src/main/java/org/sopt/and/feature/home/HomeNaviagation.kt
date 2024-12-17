package org.sopt.and.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val homeRoute = "homeRoute"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onNavigateToDetail: (Int) -> Unit
) {
    composable(route = homeRoute) {
        HomeRoute(
            onNavigateToDetail = onNavigateToDetail
        )
    }
}
