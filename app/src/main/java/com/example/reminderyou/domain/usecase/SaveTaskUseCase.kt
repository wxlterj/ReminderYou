package com.example.reminderyou.domain.usecase

import com.example.reminderyou.data.repository.TaskRepository
import com.example.reminderyou.data.repository.notification.TaskNotificationRepository
import com.example.reminderyou.domain.model.Task
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SaveTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskNotificationRepository: TaskNotificationRepository
) {
    suspend operator fun invoke(task: Task) {
        taskRepository.saveTask(task).let {
            val taskSaved = taskRepository.getTaskById(it).first()
            taskNotificationRepository.scheduleTask(taskSaved)
        }
    }
}