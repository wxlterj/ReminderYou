package com.example.reminderyou.ui.screen.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reminderyou.R
import com.example.reminderyou.data.local.fake.DataSource
import com.example.reminderyou.domain.model.Category
import com.example.reminderyou.ui.core.util.Screen
import com.example.reminderyou.ui.core.util.composables.ReminderYouTopAppBar
import com.example.reminderyou.ui.core.util.composables.TaskStatusCard
import com.example.reminderyou.ui.core.util.composables.TasksList
import com.example.reminderyou.ui.theme.ReminderYouTheme

@Composable
fun CategoryScreen(onBackButtonClicked: () -> Unit) {
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
                tasksDone = "5",
                tasksPending = "5",
                modifier = Modifier
                    .padding(16.dp)
            )
            TasksList(tasks = DataSource.tasks, onTaskItemClicked = {/*TODO*/}, modifier = Modifier.padding(horizontal = 16.dp))
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