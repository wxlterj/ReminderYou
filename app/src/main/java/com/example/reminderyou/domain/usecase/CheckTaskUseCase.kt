package com.example.reminderyou.domain.usecase

import com.example.reminderyou.data.local.dao.TaskDao
import com.example.reminderyou.data.mapper.toTask
import com.example.reminderyou.data.repository.TaskRepository
import com.example.reminderyou.domain.model.Task
import com.example.reminderyou.domain.model.TaskWithCategory
import javax.inject.Inject

class CheckTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        return taskRepository.checkTask(task)
    }
}