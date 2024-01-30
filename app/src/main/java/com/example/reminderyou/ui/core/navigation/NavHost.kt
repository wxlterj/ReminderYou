package com.example.reminderyou.ui.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.reminderyou.ui.screen.addtask.AddTaskScreen
import com.example.reminderyou.ui.screen.category.CategoryScreen
import com.example.reminderyou.ui.screen.home.HomeScreen

@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Home.route,
        enterTransition = {
            fadeIn(
                animationSpec = tween(300, easing = LinearEasing)
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        popEnterTransition = {
            fadeIn(
                animationSpec = tween(300, easing = LinearEasing)
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(300, easing = LinearEasing)
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) {
        val backStackEntry = navController.previousBackStackEntry?.destination
        val isBackStackEntry = backStackEntry == null
        composable(NavigationRoute.Home.route) {
            HomeScreen(
                onAddTaskPressed = { navController.navigate(NavigationRoute.AddTask.route) },
                onCategoryClicked = { navController.navigate("${NavigationRoute.CategoryScreen.route}/$it") },
                isBackStackEntry = isBackStackEntry
            )
        }
        composable(NavigationRoute.AddTask.route) {
            AddTaskScreen(
                onBackClicked = { navController.popBackStack() },
                onAddTaskClicked = { navController.popBackStack() }
            )
        }
        composable(
            "${NavigationRoute.CategoryScreen.route}/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) {
            CategoryScreen(
                onBackButtonClicked = { navController.popBackStack() },
                onActionButtonClicked = { navController.popBackStack() })
        }
    }
}

