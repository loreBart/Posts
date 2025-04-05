package com.test.posts.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.test.posts.R
import com.test.posts.data.model.Post
import com.test.posts.ui.screen.posts.PostsScreen
import com.test.posts.ui.screen.preferred.FavouritesScreen
import kotlinx.collections.immutable.persistentListOf

@Composable
fun NavigationBarScaffold(
    startScreen: NavigationBarScreen,
    onNavigateDetails: (Post) -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val navigationBarScreens = remember {
        persistentListOf(
            NavigationBarScreen.Posts,
            NavigationBarScreen.Favourites
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showNavigationBar = navigationBarScreens.any {
        it.route == currentDestination?.route
    }

    Scaffold(
        bottomBar = {
            if (showNavigationBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    tonalElevation = 0.dp
                ) {
                    navigationBarScreens.forEach { screen ->
                        AddNavigationBarItem(
                            screen = screen,
                            currentDestination = currentDestination,
                            navController = navController
                        )
                    }
                }
            }
        },
        content = { scaffoldPadding ->
            NavigationBarNavHost(
                navController = navController,
                startScreen = startScreen,
                onNavigateDetails = onNavigateDetails,
                modifier = Modifier.padding(scaffoldPadding)
            )
        },
        modifier = modifier
    )
}

@Composable
private fun NavigationBarNavHost(
    navController: NavHostController,
    startScreen: NavigationBarScreen,
    onNavigateDetails: (Post) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startScreen.route,
        modifier = modifier
    ) {
        composable(route = NavigationBarScreen.Posts.route) {
            PostsScreen(onNavigateDetails = onNavigateDetails)
        }
        composable(route = NavigationBarScreen.Favourites.route) {
            FavouritesScreen(onNavigateDetails = onNavigateDetails)
        }
    }
}

@Composable
private fun RowScope.AddNavigationBarItem(
    screen: NavigationBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val selected = currentDestination?.hierarchy?.any { destination ->
        destination.route == screen.route
    } == true

    NavigationBarItem(
        label = {
            Text(
                text = stringResource(screen.nameResourceId),
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
        },
        icon = {
            Icon(
                imageVector = when (screen) {
                    NavigationBarScreen.Posts -> Icons.Rounded.BarChart
                    NavigationBarScreen.Favourites -> Icons.Rounded.Favorite
                },
                contentDescription = when (screen) {
                    NavigationBarScreen.Posts -> stringResource(R.string.posts_screen)
                    NavigationBarScreen.Favourites -> stringResource(R.string.favourites_screen)
                }
            )
        },
        selected = selected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onSurface,
            selectedTextColor = MaterialTheme.colorScheme.onSurface,
            indicatorColor = MaterialTheme.colorScheme.background,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = modifier
    )
}
