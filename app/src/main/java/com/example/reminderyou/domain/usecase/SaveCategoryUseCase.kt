package com.example.reminderyou.domain.usecase

import com.example.reminderyou.data.repository.CategoryRepository
import com.example.reminderyou.domain.model.Category
import javax.inject.Inject

class SaveCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        categoryRepository.saveCategory(category)
    }
}