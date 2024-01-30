package com.example.reminderyou.util.alarms

import com.example.reminderyou.domain.model.Task

interface AlarmScheduler {
    fun schedule(task: Task)
    fun cancel(task: Task)

    fun getAlarm()
}