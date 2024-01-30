package com.example.reminderyou.util.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.reminderyou.R
import com.example.reminderyou.util.CHANNEL_ID
import com.example.reminderyou.util.TASK_NOTIFICATION_SUMMARY_TEXT

fun makeTaskNotification(taskId: Int, title: String?, context: Context) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setStyle(
            NotificationCompat.BigTextStyle().bigText(title).setSummaryText(
                TASK_NOTIFICATION_SUMMARY_TEXT
            )
        )
        .setSmallIcon(R.drawable.baseline_task_alt_24)
        .setContentText(title)

    notificationManager.notify(taskId, builder.build())
}