package com.example.reminderyou.ui.core.util.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.example.reminderyou.R
import com.example.reminderyou.ui.theme.ReminderYouTheme
import com.example.reminderyou.util.timeFormatter
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsBottomSheet(
    onDismissRequest: () -> Unit,
    taskTitle: String,
    taskDescription: String?,
    taskDate: String,
    taskTime: String,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
            scope.launch {
                sheetState.hide()
            }
        },
        modifier = modifier,
        sheetState = sheetState
    ) {
        TaskDetails(
            taskTitle = taskTitle,
            taskDescription = taskDescription,
            date = taskDate,
            time = taskTime,
            onEditClicked = onEditClicked,
            onDeleteClicked = onDeleteClicked,
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
fun TaskDetails(
    taskTitle: String,
    taskDescription: String?,
    date: String,
    time: String,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(text = taskTitle, style = MaterialTheme.typography.titleLarge)
        TaskDetailsDescription(taskDescription, modifier = Modifier.padding(vertical = 16.dp))
        TaskDetailsDateAndTime(
            date = date,
            time = time,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TaskDetailsButtons(
            onEditClicked = onEditClicked,
            onDeleteClicked = onDeleteClicked,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun TaskDetailsDescription(taskDescription: String?, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.add_task_description),
            modifier = Modifier.padding(bottom = 4.dp),
            style = MaterialTheme.typography.titleMedium
        )
        taskDescription?.let { description ->
            Card {
                Text(
                    text = description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun TaskDetailsDateAndTime(date: String, time: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(108.dp)
    ) {
        Column {
            Text(text = stringResource(R.string.date))
            Text(text = date)
        }
        Column {
            Text(text = stringResource(R.string.time))
            Text(text = time)
        }
    }
}

@Composable
fun TaskDetailsButtons(
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Button(onClick = onEditClicked, modifier = Modifier.weight(1f)) {
            Text(text = stringResource(R.string.edit))
        }
        Spacer(modifier = Modifier.weight(0.05f))
        Button(
            onClick = onDeleteClicked,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(text = stringResource(R.string.delete))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskDetailsPreview() {
    ReminderYouTheme {
        TaskDetails(
            taskTitle = "Do something",
            taskDescription = LoremIpsum(1).values.first(),
            date = LocalDate.now().toString(),
            time = LocalTime.now().format(timeFormatter).toString(),
            onEditClicked = {},
            onDeleteClicked = {}
        )
    }
}