package com.example.reminderyou.domain.usecase

import com.example.reminderyou.data.repository.CategoryRepository
import com.example.reminderyou.domain.model.Category
import com.example.reminderyou.domain.model.TaskWithCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(categoryId: Int): Pair<List<TaskWithCategory>, Category>{
        val tasksWithCategory: MutableList<TaskWithCategory> =
            emptyList<TaskWithCategory>().toMutableList()

        val category = categoryRepository.getCategoryById(categoryId).first()
        category.tasks?.forEach { task ->
            tasksWithCategory.add(TaskWithCategory(task = task, category = category))
        }

        return Pair(tasksWithCategory.toList(), category)
    }
}