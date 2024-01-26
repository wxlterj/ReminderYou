package com.example.reminderyou.domain.usecase

import com.example.reminderyou.data.repository.CategoryRepository
import com.example.reminderyou.domain.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(categoryId: Int): Flow<Category> {
        return categoryRepository.getCategoryById(categoryId)
    }
}