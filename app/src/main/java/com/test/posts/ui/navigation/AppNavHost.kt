package com.test.posts.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.posts.data.model.Post
import com.test.posts.ui.screen.postdetails.PostDetailsScreen

@Composable
fun AppNavHost(
    navigationBarStartScreen: NavigationBarScreen = NavigationBarScreen.Posts,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.NavigationBar.route
    ) {
        composable(route = Screen.NavigationBar.route) {
            NavigationBarScaffold(
                startScreen = navigationBarStartScreen,
                onNavigateDetails = { post: Post ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("post", post)
                    Log.d("###", "POST 111 ----------> $post")
                    navController.navigate(Screen.Details.route + "/${post.id}")
                }
            )
        }
        composable(route = Screen.Details.route + "/{postId}") {
            val post = navController.previousBackStackEntry?.savedStateHandle?.get<Post>("post")
            Log.d("###", "POST 222 ----------> $post")
            post?.let {
                PostDetailsScreen(post, onNavigateUp = { navController.navigateUp() })
            }
        }
    }
}
