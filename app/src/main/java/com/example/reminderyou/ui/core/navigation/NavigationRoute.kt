package com.example.reminderyou.ui.core.navigation

sealed class NavigationRoute(val route: String) {
    data object Home: NavigationRoute("home")
    data object AddTask: NavigationRoute("add_task")
    data object CategoryScreen: NavigationRoute("category_screen")
}