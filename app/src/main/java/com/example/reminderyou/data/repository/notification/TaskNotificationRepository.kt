package com.example.reminderyou.data.repository.notification

import com.example.reminderyou.domain.model.Task
import com.example.reminderyou.util.alarms.AndroidAlarmScheduler
import javax.inject.Inject

class TaskNotificationRepository @Inject constructor(
    private val alarmScheduler: AndroidAlarmScheduler
) {
    fun scheduleTask(task: Task) {
        alarmScheduler.schedule(task)
    }

    fun cancelTask(task: Task) {
        alarmScheduler.cancel(task)
    }
}