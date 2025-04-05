package com.test.posts.ui.navigation

import androidx.annotation.StringRes
import com.test.posts.R

sealed class Screen(val route: String) {
    data object NavigationBar : Screen("navigation_bar_screen")
    data object Details : Screen("details_screen")
}

sealed class NavigationBarScreen(
    val route: String,
    @StringRes val nameResourceId: Int
) {
    data object Posts : NavigationBarScreen(
        route = "posts_screen",
        nameResourceId = R.string.posts_screen
    )

    data object Favourites : NavigationBarScreen(
        route = "favourites_screen",
        nameResourceId = R.string.favourites_screen
    )
}
