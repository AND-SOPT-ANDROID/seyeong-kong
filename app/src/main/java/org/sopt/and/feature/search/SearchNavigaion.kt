package org.sopt.and.feature.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val searchRoute = "searchRoute"

fun NavGraphBuilder.searchScreen() {
    composable(route = searchRoute) {
        SearchRoute()
    }
}
