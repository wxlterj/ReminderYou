package com.example.reminderyou.util.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.reminderyou.util.notifications.makeTaskNotification
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val taskId = intent?.getIntExtra("TASK_ID", 0) ?: 0
        val taskTitle = intent?.getStringExtra("TASK_TITLE")
        makeTaskNotification(taskId, taskTitle, context)
    }
}