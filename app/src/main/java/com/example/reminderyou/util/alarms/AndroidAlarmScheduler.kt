package com.example.reminderyou.util.alarms

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.reminderyou.domain.model.Task
import com.example.reminderyou.util.toLong
import javax.inject.Inject

/** Should be called "AlarmSchedulerImpl" and be in the data layer */
class AndroidAlarmScheduler @Inject constructor(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)


    @SuppressLint("MissingPermission")
    override fun schedule(task: Task) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("TASK_ID", task.id)
            putExtra("TASK_TITLE", task.title)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC,
            task.deadline.toLong(),
            PendingIntent.getBroadcast(
                context,
                task.id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        Log.d("CANCELAR", "TAREA CON EL ID ${task.id} scheduleada")
    }

    override fun cancel(task: Task) {
        val intent = Intent(context, AlarmReceiver::class.java)
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                task.id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        Log.d("CANCELAR", "TAREA CON EL ID ${task.id} cancelada")
    }

    override fun getAlarm() {

    }
}