package com.dan.page3withjepackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dan.page3withjepackcompose.screens.home.HomeScreen

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.router) {
        composable(route = Screen.Home.router) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Search.router) {
//            SearchScreen(navController = navController)
        }
    }
}