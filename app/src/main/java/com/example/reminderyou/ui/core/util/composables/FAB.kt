package com.example.reminderyou.ui.core.util.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.reminderyou.R
import com.example.reminderyou.util.FabType

@Composable
fun ReminderYouFAB(onFabButtonPressed: () -> Unit, fabType: FabType, modifier: Modifier = Modifier) {
    if (fabType == FabType.NORMAL) {
        FloatingActionButton(onClick =  onFabButtonPressed) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add_task))
        }
    } else {
        ExtendedFloatingActionButton(onClick = onFabButtonPressed) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add_task))
            Text(text = stringResource(R.string.add_task))
        }
    }
}