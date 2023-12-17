package com.kevin.githubsearch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kevin.githubsearch.homescreen.HomeScreen
import com.kevin.githubsearch.repodetail.RepoDetail

@Composable
fun GitHubNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                    onRepoClick = {
                        navController.navigate(
                                Screen.RepoDetail.createRoute(
                                        repoId = it
                                )
                        )
                    }
            )
        }
        composable(
                route = Screen.RepoDetail.route,
                arguments = Screen.RepoDetail.navArguments
        ) {
            RepoDetail(
                    onBackClick = { navController.navigateUp() }
            )
        }
    }
}