package com.kevin.githubsearch.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
        val route: String,
        val navArguments: List<NamedNavArgument> = emptyList()
) {

    data object Home : Screen("Home")
    data object RepoDetail : Screen(
            route = "repoDetail/{repoId}",
            navArguments = listOf(navArgument("repoId") {
                type = NavType.StringType
            })

    ) {
        fun createRoute(repoId: String) = "repoDetail/${repoId}"
    }
}