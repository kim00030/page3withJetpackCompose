package com.dan.page3withjepackcompose.navigation

sealed class Screen(val router: String) {
    object Home : Screen(router = "home_screen")
    object Search : Screen(router = "search_screen")
}
