package com.example.reminderyou.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reminderyou.ui.screen.addtask.AddTaskScreen
import com.example.reminderyou.ui.screen.category.CategoryScreen
import com.example.reminderyou.ui.screen.home.HomeScreen

@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Home.route
    ) {
        composable(NavigationRoute.Home.route) {
            HomeScreen()
        }
        composable(NavigationRoute.AddTask.route) {
            AddTaskScreen()
        }
        composable(NavigationRoute.CategoryScreen.route) {
            CategoryScreen(onBackButtonClicked = {})
        }
    }
}