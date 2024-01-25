package com.example.reminderyou.domain.usecase

import com.example.reminderyou.data.repository.CategoryRepository
import com.example.reminderyou.domain.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>> {
        return categoryRepository.getCategories()
    }
}