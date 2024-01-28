package com.example.reminderyou.domain.usecase

import com.example.reminderyou.data.repository.TaskRepository
import com.example.reminderyou.domain.model.Task
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.deleteTask(task)
    }
}