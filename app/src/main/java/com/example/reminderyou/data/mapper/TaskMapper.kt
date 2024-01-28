package com.example.reminderyou.data.mapper

import com.example.reminderyou.data.local.entity.TaskEntity
import com.example.reminderyou.domain.model.Task
import com.example.reminderyou.domain.model.TaskWithCategory
import com.example.reminderyou.util.longToLocalDateTime
import com.example.reminderyou.util.toLong

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

fun TaskWithCategory.toTask(): Task {
    return Task(
        id = task.id,
        title = task.title,
        description = task.description,
        deadline = task.deadline,
        isChecked = task.isChecked,
        categoryId = category?.id
    )
}