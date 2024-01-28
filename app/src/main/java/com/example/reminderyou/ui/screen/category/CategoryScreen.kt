package com.example.reminderyou.ui.screen.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.reminderyou.ui.core.util.Screen
import com.example.reminderyou.ui.core.util.composables.ReminderYouTopAppBar
import com.example.reminderyou.ui.core.util.composables.TaskStatusCard
import com.example.reminderyou.ui.core.util.composables.TasksList

@Composable
fun CategoryScreen(
    onBackButtonClicked: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ReminderYouTopAppBar(
                currentScreen = Screen.Category,
                onNavigationIconClicked = onBackButtonClicked
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Work",
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.headlineLarge
            )
            TaskStatusCard(
                tasksDone = uiState.tasks.filter { it.task.isChecked }.size.toString(),
                tasksPending = uiState.tasks.filter { !it.task.isChecked }.size.toString(),
                modifier = Modifier
                    .padding(16.dp)
            )
            TasksList(
                tasksWithCategory = uiState.tasks,
                onTaskItemClicked = { /*TODO*/ },
                onTaskChecked = { task, isChecked -> viewModel.pass(task, isChecked) },
                onTaskDeleted = {}
            )
        }
    }
}

//@Preview
//@Composable
//fun CategoryScreenPreview() {
//    ReminderYouTheme {
//        CategoryScreen()
//    }
//}