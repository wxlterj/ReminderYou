package com.example.reminderyou.data.mapper

import com.example.reminderyou.data.local.entity.TaskEntity
import com.example.reminderyou.domain.model.Task
import com.example.reminderyou.util.longToLocalDateTime
import com.example.reminderyou.util.toLong
import java.time.ZoneId
import java.time.ZoneOffset

fun Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        taskId = id,
        title = this.title,
        description = this.description,
        deadline = this.deadline.toLong(),
        isDone = isChecked,
        categoryId = this.categoryId
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = taskId,
        title = this.title,
        description = this.description,
        deadline = longToLocalDateTime(this.deadline),
        isChecked = isDone,
        categoryId = this.categoryId
    )
}