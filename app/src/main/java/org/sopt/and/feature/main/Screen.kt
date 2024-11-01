package org.sopt.and.feature.main

import androidx.annotation.StringRes
import org.sopt.and.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    data object Home : Screen("home", R.string.home)
    data object Search : Screen("search", R.string.search)
    data object Profile : Screen("profile", R.string.profile)
}
