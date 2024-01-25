package com.example.reminderyou.data.repository

import com.example.reminderyou.data.local.dao.TaskDao
import com.example.reminderyou.data.local.entity.TaskEntity
import com.example.reminderyou.data.mapper.toTaskEntity
import com.example.reminderyou.domain.model.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    suspend fun saveTask(task: Task) {
        taskDao.saveTask(task.toTaskEntity())
    }
}