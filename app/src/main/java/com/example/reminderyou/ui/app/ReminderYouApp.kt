package com.example.reminderyou.ui.app

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.reminderyou.ui.screen.addtask.AddTaskScreen
import com.example.reminderyou.ui.screen.home.HomeScreen

@Composable
fun ReminderYouApp(windowWidthSizeClass: WindowWidthSizeClass) {
    HomeScreen()
}