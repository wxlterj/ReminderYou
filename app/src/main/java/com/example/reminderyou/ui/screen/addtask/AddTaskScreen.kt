package com.example.reminderyou.ui.screen.addtask

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reminderyou.R
import com.example.reminderyou.ui.theme.ReminderYouTheme
import com.example.reminderyou.util.dateFormatter
import com.example.reminderyou.util.timeFormatter
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    modifier: Modifier = Modifier,
    viewModel: AddTaskViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

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

    Box(modifier = modifier.fillMaxSize()) {
        AddTaskForm(
            title = "",
            date = uiState.datePicked.format(dateFormatter),
            time = uiState.timePicked/*.format(timeFormatter)*/,
            dateInteractionSource = dateInteractionSource,
            timeInteractionSource = timeInteractionSource,
            modifier = Modifier.fillMaxSize()
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
                    }, modifier = Modifier.align(Alignment.End)) {
                        Text(text = stringResource(R.string.confirm))
                    }
                }
            }
        }
    }
}

@Composable
fun AddTaskForm(
    title: String,
    date: String,
    time: String,
    dateInteractionSource: MutableInteractionSource,
    timeInteractionSource: MutableInteractionSource,
    modifier: Modifier = Modifier
) {
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
            onValueChange = {},
            modifier = Modifier.width(280.dp),
            label = {
                Text(text = stringResource(R.string.add_task_title))
            },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = stringResource(R.string.add_task_description)) },
            maxLines = 8,
            minLines = 8
        )
        Spacer(modifier = Modifier.height(8.dp))
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
        Spacer(modifier = Modifier.height(8.dp))
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
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { /*TODO*/ }, modifier = Modifier.width(280.dp)) {
            Text(text = stringResource(R.string.add_task))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddTaskScreenPreview() {
    ReminderYouTheme {
        AddTaskScreen()
    }
}