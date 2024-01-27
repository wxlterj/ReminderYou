package com.example.reminderyou.domain.usecase

import com.example.reminderyou.data.repository.CategoryRepository
import com.example.reminderyou.data.repository.TaskRepository
import com.example.reminderyou.domain.model.TaskWithCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class GetTasksWithCategoryUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
) {

    suspend operator fun invoke(): List<TaskWithCategory> {
        val tasksWithCategory: MutableList<TaskWithCategory> =
            emptyList<TaskWithCategory>().toMutableList()

        taskRepository.getTasks().first().forEach { task ->
            val category = task.categoryId?.let {
                categoryRepository.getCategoryById(it).first()
            }

            tasksWithCategory.add(TaskWithCategory(task, category))
        }

        return tasksWithCategory.toList()
    }

}