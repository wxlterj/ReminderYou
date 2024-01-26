package com.example.reminderyou.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reminderyou.R
import com.example.reminderyou.data.local.fake.DataSource
import com.example.reminderyou.domain.model.Category
import com.example.reminderyou.domain.model.Task
import com.example.reminderyou.domain.model.TaskWithCategory
import com.example.reminderyou.ui.core.util.Screen
import com.example.reminderyou.ui.core.util.composables.ReminderYouFAB
import com.example.reminderyou.ui.core.util.composables.ReminderYouTopAppBar
import com.example.reminderyou.ui.core.util.composables.TaskDetailsBottomSheet
import com.example.reminderyou.ui.core.util.composables.TaskStatusCard
import com.example.reminderyou.ui.core.util.composables.TasksList
import com.example.reminderyou.ui.theme.ReminderYouTheme
import com.example.reminderyou.util.FabType
import com.example.reminderyou.util.timeFormatter
import kotlinx.coroutines.launch
import java.time.LocalTime

@Composable
fun HomeScreen(
    onAddTaskPressed: () -> Unit,
    onCategoryClicked: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

            }
        }
    ) {
        Scaffold(
            topBar = {
                ReminderYouTopAppBar(
                    currentScreen = Screen.Home,
                    onNavigationIconClicked = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                if (uiState is HomeUiState.Success) {
                    ReminderYouFAB(
                        onFabButtonPressed = onAddTaskPressed,
                        fabType = FabType.EXTENDED
                    )
                }
            }
        ) { innerPadding ->
            when (uiState) {
                is HomeUiState.Success -> {
                    val currentState = (uiState as HomeUiState.Success)
                    HomeScreenSuccess(
                        toDoTasks = currentState.tasks,
                        categories = currentState.categories,
                        showTaskDetails = currentState.showTaskDetails,
                        onTaskItemClicked = { viewModel.onTaskItemClicked() },
                        onDismissRequest = { viewModel.onTaskBottomSheetClosed() },
                        onTaskChecked = { task, isChecked -> viewModel.checkTask(task, isChecked) },
                        onCategoryClicked = onCategoryClicked,
                        onTaskDeleted = { task -> viewModel.deleteTask(task) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                is HomeUiState.Loading -> {
                    HomeScreenLoading()
                }

                is HomeUiState.Error -> {
                    HomeScreenError(onRetryClicked = { })
                }
            }
        }
    }
}

@Composable
fun HomeScreenSuccess(
    toDoTasks: List<TaskWithCategory>,
    categories: List<Category>,
    showTaskDetails: Boolean,
    onTaskItemClicked: () -> Unit,
    onDismissRequest: () -> Unit,
    onTaskChecked: (TaskWithCategory, Boolean) -> Unit,
    onCategoryClicked: () -> Unit,
    onTaskDeleted: (TaskWithCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TaskStatusCard(
            tasksDone = toDoTasks.filter { it.task.isChecked }.size.toString(),
            tasksPending = toDoTasks.filter { !it.task.isChecked }.size.toString(),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
        CategoriesList(categories = categories, onCategoryClicked = onCategoryClicked)
        TasksList(
            tasksWithCategory = toDoTasks,
            onTaskItemClicked = onTaskItemClicked,
            onTaskChecked = onTaskChecked,
            onTaskDeleted = onTaskDeleted,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }

    if (showTaskDetails) {
        TaskDetailsBottomSheet(
            onDismissRequest = onDismissRequest,
            taskTitle = "",
            taskDescription = "",
            taskDate = "",
            taskTime = LocalTime.now().format(timeFormatter).toString(),
            onEditClicked = { /*TODO*/ },
            onDeleteClicked = { /*TODO*/ }
        )
    }
}

@Composable
fun HomeScreenLoading(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun HomeScreenError(onRetryClicked: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.error_16_svgrepo_com),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .weight(2f)
        )
        ErrorInformation(onRetryClicked = onRetryClicked, modifier = Modifier.weight(1f))
    }
}

@Composable
fun ErrorInformation(onRetryClicked: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.connection_error),
            style = MaterialTheme.typography.displaySmall
        )
        Text(text = stringResource(R.string.connection_error_message_1))
        Text(text = stringResource(R.string.connection_error_message_2))
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetryClicked, modifier = Modifier.fillMaxWidth(0.6f)) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
fun CategoriesList(
    categories: List<Category>,
    onCategoryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.categories),
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(categories, key = { it.id }) { category ->
                CategoryCard(
                    categoryName = category.name,
                    categoryTasks = category.tasksQuantity,
                    onCategoryClicked = onCategoryClicked
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    categoryName: String,
    categoryTasks: Int,
    onCategoryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.sizeIn(minWidth = 150.dp, maxWidth = 220.dp, minHeight = 140.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(2.dp, Brush.verticalGradient(listOf(Color.Blue, Color.Cyan))),
        onClick = onCategoryClicked
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = categoryName,
                    modifier = Modifier.padding(top = 7.dp, start = 8.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = onCategoryClicked) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(
                            R.string.category_card_content_description,
                            categoryName
                        )
                    )
                }
            }
            Text(
                text = stringResource(R.string.category_task_number, categoryTasks),
                modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenErrorPreview() {
    ReminderYouTheme {
        HomeScreenError(onRetryClicked = {})
    }
}