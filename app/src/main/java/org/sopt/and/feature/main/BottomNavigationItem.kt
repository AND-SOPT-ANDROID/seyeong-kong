package org.sopt.and.feature.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import org.sopt.and.R

sealed class BottomNavigationItem(
    val route: String,
    @StringRes val titleResId: Int,
    val icon: ImageVector
) {
    data object Home : BottomNavigationItem(
        route = "homeRoute",
        titleResId = R.string.home,
        icon = Icons.Default.Home
    )
    
    data object Search : BottomNavigationItem(
        route = "searchRoute",
        titleResId = R.string.search,
        icon = Icons.Default.Search
    )
    
    data object Profile : BottomNavigationItem(
        route = "myPageRoute",
        titleResId = R.string.profile,
        icon = Icons.Default.Person
    )
}

val bottomNavigationItems = listOf(
    BottomNavigationItem.Home,
    BottomNavigationItem.Search,
    BottomNavigationItem.Profile
)
