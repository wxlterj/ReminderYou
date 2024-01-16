package com.example.reminderyou.ui.core.util

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.reminderyou.R

sealed class NavigationIcon(val icon: ImageVector, @StringRes val description: Int) {
    data object Menu : NavigationIcon(Icons.Filled.Menu, R.string.menu)
    data object Back : NavigationIcon(Icons.Filled.ArrowBack, R.string.back)
}