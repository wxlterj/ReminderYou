package com.example.reminderyou.domain.usecase

import com.example.reminderyou.data.local.dao.TaskDao
import com.example.reminderyou.data.mapper.toTask
import com.example.reminderyou.data.repository.TaskRepository
import com.example.reminderyou.data.repository.notification.TaskNotificationRepository
import com.example.reminderyou.domain.model.Task
import com.example.reminderyou.domain.model.TaskWithCategory
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class CheckTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskNotificationRepository: TaskNotificationRepository
) {
    suspend operator fun invoke(task: Task) {
        if (task.isChecked) {
            taskNotificationRepository.cancelTask(task)
        } else {
            if (task.deadline.isAfter(LocalDateTime.now(ZoneId.systemDefault()))) {
                taskNotificationRepository.scheduleTask(task)
            }
        }
        return taskRepository.checkTask(task)
    }
}