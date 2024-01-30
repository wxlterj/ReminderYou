package com.example.reminderyou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.reminderyou.domain.model.Task
import com.example.reminderyou.ui.app.ReminderYouApp
import com.example.reminderyou.ui.core.navigation.Navigation
import com.example.reminderyou.ui.theme.ReminderYouTheme
import com.example.reminderyou.util.alarms.AndroidAlarmScheduler
import com.example.reminderyou.util.longToLocalDateTime
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.ZoneId

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowWidthSize = calculateWindowSizeClass(this).widthSizeClass
            ReminderYouTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReminderYouApp(windowWidthSizeClass = windowWidthSize)
                }
            }
        }
    }
}

