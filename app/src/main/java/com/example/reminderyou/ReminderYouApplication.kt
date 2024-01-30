package com.example.reminderyou

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import com.example.reminderyou.util.CHANNEL_ID
import com.example.reminderyou.util.NOTIFICATION_CHANNEL_DESCRIPTION
import com.example.reminderyou.util.NOTIFICATION_CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ReminderYouApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createTaskNotificationChannel()
    }

    private fun createTaskNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = NOTIFICATION_CHANNEL_DESCRIPTION

        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}