package com.example.reminderyou.ui.core.util.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.reminderyou.R
import com.example.reminderyou.util.dateFormatter
import java.time.ZonedDateTime

@Composable
fun TaskStatusCard(tasksDone: String, tasksPending: String, modifier: Modifier = Modifier) {
    val currentDate =
        ZonedDateTime.now().toLocalDate()
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(2.dp, Brush.verticalGradient(listOf(Color.Blue, Color.Cyan)))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.your_tasks),
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = currentDate.format(dateFormatter).toString(),
                    modifier = Modifier.padding(end = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Divider()
            TasksStatus(
                tasksDone = tasksDone,
                tasksPending = tasksPending,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}

@Composable
fun TasksStatus(tasksDone: String, tasksPending: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TaskStatusIndicator(
            image = R.drawable.task_complete,
            status = R.string.done,
            tasksQuantity = tasksDone
        )
        Spacer(modifier = Modifier.width(64.dp))
        TaskStatusIndicator(
            image = R.drawable.task_in_progress,
            status = R.string.pending,
            tasksQuantity = tasksPending
        )
    }
}

@Composable
fun TaskStatusIndicator(
    @DrawableRes image: Int,
    @StringRes status: Int,
    tasksQuantity: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskStatusImage(image = image)
        Text(text = stringResource(status))
        Text(text = tasksQuantity)
    }
}

@Composable
fun TaskStatusImage(@DrawableRes image: Int, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            contentScale = ContentScale.Crop
        )
    }
}