package com.example.reminderyou.ui.core.util

enum class Screen(val navigationIcon: NavigationIcon, val actionIcon: NavigationIcon) {
    Home(NavigationIcon.Menu, NavigationIcon.Notification),
    AddTask(NavigationIcon.Back, NavigationIcon.Notification),
    Category(NavigationIcon.Back, NavigationIcon.Delete)
}