package com.example.reminderyou.data.repository

import com.example.reminderyou.data.local.dao.TaskDao
import com.example.reminderyou.data.mapper.toTask
import com.example.reminderyou.data.mapper.toTaskEntity
import com.example.reminderyou.domain.model.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks()
            .mapLatest { tasks -> tasks.map { taskEntity -> taskEntity.toTask() } }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTaskById(taskId: Long): Flow<Task> {
        return taskDao.getTaskById(taskId).mapLatest { it.toTask() }
    }

    suspend fun saveTask(task: Task) {
        taskDao.saveTask(task.toTaskEntity())
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toTaskEntity())
    }

    suspend fun checkTask(task: Task) {
        taskDao.checkTask(task.toTaskEntity())
    }
}