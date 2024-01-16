package com.example.reminderyou.ui.core.util.composables

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.reminderyou.ui.core.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderYouTopAppBar(
    currentScreen: Screen,
    onNavigationIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {/* DON'T HAVE TITLE */ },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onNavigationIconClicked,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = currentScreen.navigationIcon.icon,
                    contentDescription = stringResource(currentScreen.navigationIcon.description),
                )
            }
        },
        actions = {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notifications",
                modifier = Modifier.padding(end = 16.dp)
            )
        },
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    )
}