package com.example.reminderyou.ui.screen.addtask

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.reminderyou.R
import com.example.reminderyou.domain.model.Category
import com.example.reminderyou.ui.core.util.Screen
import com.example.reminderyou.ui.core.util.composables.AddCategoryBottomSheet
import com.example.reminderyou.ui.core.util.composables.ReminderYouTopAppBar
import com.example.reminderyou.ui.theme.ReminderYouTheme
import com.example.reminderyou.util.LENGTH_MAX_DESCRIPTION
import com.example.reminderyou.util.LENGTH_MAX_TITLE
import com.example.reminderyou.util.dateFormatter
import kotlinx.coroutines.launch
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onBackClicked: () -> Unit,
    onAddTaskClicked: () -> Unit,
    viewModel: AddTaskViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    // Date
    val datePickerState = rememberDatePickerState()
    val date = datePickerState.selectedDateMillis

    // Time
    val timePickerState = rememberTimePickerState(
        initialHour = LocalTime.now().hour,
        initialMinute = LocalTime.now().minute
    )
    val timeHour = timePickerState.hour
    val timeMinute = timePickerState.minute

    // Date interactions
    val dateInteractionSource = remember { MutableInteractionSource() }
    val dateInteractions = remember { mutableStateListOf<Interaction>() }
    val isDatePressed = dateInteractions.isNotEmpty()

    // Time interactions
    val timeInteractionSource = remember { MutableInteractionSource() }
    val timeInteractions = remember { mutableStateListOf<Interaction>() }
    val isTimePressed = timeInteractions.isNotEmpty()


    LaunchedEffect(key1 = dateInteractionSource) {
        dateInteractionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> dateInteractions.add(interaction)
            }
        }
    }

    LaunchedEffect(key1 = timeInteractionSource) {
        timeInteractionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> timeInteractions.add(interaction)

            }
        }
    }

    Scaffold(
        topBar = {
            ReminderYouTopAppBar(
                currentScreen = Screen.AddTask,
                onNavigationIconClicked = onBackClicked,
                onActionButtonClicked = {}
            )
        }
    ) { innerPadding ->
        AddTaskForm(
            title = viewModel.titleState,
            description = viewModel.descriptionState,
            date = uiState.datePicked,
            time = uiState.timePicked,
            category = uiState.categorySelected?.name ?: "",
            categories = uiState.categories,
            showMenu = uiState.showCategoryMenu,
            onTitleChange = { viewModel.changeTitle(it) },
            onCategoryMenuClicked = {
                viewModel.showCategories(it)
                if (uiState.showCategoryMenu) focusManager.clearFocus()
            },
            onCategoryClicked = {
                viewModel.selectCategory(it)
                focusManager.clearFocus()
            },
            onAddNewCategoryClicked = {
                viewModel.showAddCategory()
                focusManager.clearFocus()
            },
            onDescriptionChange = viewModel::changeDescription,
            onAddTaskClicked = {
                onAddTaskClicked()
                viewModel.addTask()
            },
            dateInteractionSource = dateInteractionSource,
            timeInteractionSource = timeInteractionSource,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }



    if (isDatePressed) {
        DatePickerDialog(
            onDismissRequest = {
                dateInteractions.removeFirst()
            },
            confirmButton = {
                TextButton(onClick = {
                    date?.let {
                        viewModel.updateDatePicked(it)
                    }
                    dateInteractions.removeFirst()
                    focusManager.clearFocus()
                }) {
                    Text(text = stringResource(R.string.confirm))
                }
            },
            properties = DialogProperties(dismissOnClickOutside = true)
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }

    if (isTimePressed) {
        Dialog(onDismissRequest = { timeInteractions.removeFirst() }) {
            Card {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    TimePicker(state = timePickerState)
                    TextButton(onClick = {
                        viewModel.updateTimePicked(timeHour, timeMinute)
                        timeInteractions.removeFirst()
                        focusManager.clearFocus()
                    }, modifier = Modifier.align(Alignment.End)) {
                        Text(text = stringResource(R.string.confirm))
                    }
                }
            }
        }
    }

    if (uiState.showAddCategory) {
        AddCategoryBottomSheet(
            categoryName = viewModel.categoryName,
            onCategoryNameChange = viewModel::changeCategoryName,
            onSaveCategoryClicked = viewModel::saveCategory,
            onAddCategoryDismiss = viewModel::closeAddCategory
        )
    }
}

@Composable
fun AddTaskForm(
    title: String,
    description: String,
    date: String,
    time: String,
    category: String,
    categories: List<Category>,
    showMenu: Boolean,
    onTitleChange: (String) -> Unit,
    onCategoryMenuClicked: (Boolean) -> Unit,
    onCategoryClicked: (Category) -> Unit,
    onAddNewCategoryClicked: () -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddTaskClicked: () -> Unit,
    dateInteractionSource: MutableInteractionSource,
    timeInteractionSource: MutableInteractionSource,
    modifier: Modifier = Modifier
) {
    val titleFocusRequester = remember { FocusRequester() }
    var isLongTitle by remember { mutableStateOf(false) }
    var isLongDescription by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.add_new_task),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = title,
            onValueChange = {
                onTitleChange(it.take(LENGTH_MAX_TITLE))
                isLongTitle = it.length >= LENGTH_MAX_TITLE + 1
            },
            modifier = Modifier
                .width(280.dp)
                .focusRequester(titleFocusRequester),
            label = {
                Text(text = stringResource(R.string.add_task_title))
            },
            singleLine = true,
            supportingText = {
                if (isLongTitle) Text(
                    text = stringResource(
                        R.string.title_length_message,
                        LENGTH_MAX_TITLE
                    )
                )
            },
            isError = isLongTitle,
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
        )
        CategoryMenu(
            category = category,
            categories = categories,
            showMenu = showMenu,
            onCategoryMenuClicked = { onCategoryMenuClicked(it) },
            onCategoryClicked = { onCategoryClicked(it) },
            onCategoryChange = { /*TODO*/ },
            onAddNewCategoryClicked = onAddNewCategoryClicked
        )
        Spacer(modifier = Modifier.height(18.dp))
        OutlinedTextField(
            value = description,
            onValueChange = {
                onDescriptionChange(it.take(LENGTH_MAX_DESCRIPTION))
                isLongDescription = it.length >= LENGTH_MAX_DESCRIPTION + 1
            },
            modifier = Modifier.width(280.dp),
            label = { Text(text = stringResource(R.string.add_task_description)) },
            supportingText = {
                if (isLongDescription) {
                    Text(
                        text = stringResource(
                            R.string.description_length_message,
                            LENGTH_MAX_DESCRIPTION
                        )
                    )
                }
            },
            isError = isLongDescription,
            maxLines = 5,
            minLines = 4
        )
        OutlinedTextField(
            value = date,
            onValueChange = {},
            label = {
                Text(text = stringResource(R.string.date))
            },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = stringResource(R.string.date)
                )
            },
            interactionSource = dateInteractionSource
        )
        Spacer(modifier = Modifier.height(17.dp))
        OutlinedTextField(
            value = time,
            onValueChange = {},
            label = { Text(text = stringResource(R.string.time)) },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = stringResource(R.string.time)
                )
            },
            interactionSource = timeInteractionSource
        )
        Spacer(modifier = Modifier.height(17.dp))
        Button(onClick = onAddTaskClicked, modifier = Modifier.width(280.dp)) {
            Text(text = stringResource(R.string.add_task))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryMenu(
    category: String,
    categories: List<Category>,
    showMenu: Boolean,
    onCategoryMenuClicked: (Boolean) -> Unit,
    onCategoryClicked: (Category) -> Unit,
    onCategoryChange: () -> Unit,
    onAddNewCategoryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = showMenu,
        onExpandedChange = { onCategoryMenuClicked(it) },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = category,
            onValueChange = { onCategoryChange() },
            modifier = Modifier
                .menuAnchor(),
            label = { Text(text = stringResource(R.string.category)) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = showMenu)
            },
        )
        ExposedDropdownMenu(
            expanded = showMenu,
            onDismissRequest = {
                onCategoryMenuClicked(false)
            },
        ) {
            categories.forEach {
                DropdownMenuItem(
                    text = { Text(text = it.name) },
                    onClick = { onCategoryClicked(it) })
            }
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.add_new_category)) },
                onClick = onAddNewCategoryClicked,
                trailingIcon = {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            )
        }
    }
}


@Composable
fun AddCategory(
    categoryName: String,
    onCategoryNameChange: (String) -> Unit,
    onSaveCategoryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isLongName by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.add_new_category),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(17.dp))
        OutlinedTextField(
            value = categoryName,
            onValueChange = {
                onCategoryNameChange(it.take(LENGTH_MAX_TITLE))
                isLongName = it.length >= LENGTH_MAX_TITLE + 1
            },
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            label = { Text(text = stringResource(R.string.name)) },
            supportingText = {
                if (isLongName) {
                    Text(text = stringResource(R.string.title_length_message, LENGTH_MAX_TITLE))
                }
            }
        )
        Button(onClick = onSaveCategoryClicked, modifier = Modifier) {
            Text(text = stringResource(R.string.save_category))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddCategoryPreview() {
    ReminderYouTheme {
        AddCategory("", {}, {})
    }
}