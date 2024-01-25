package com.example.reminderyou.ui.screen.home

import com.example.reminderyou.data.local.fake.DataSource
import com.example.reminderyou.domain.model.Category
import com.example.reminderyou.domain.model.Task

sealed class HomeUiState {
    data class Success(
        val showTaskDetails: Boolean = false,
        val tasks: List<Task> = emptyList(),
        val categories: List<Category> = DataSource.categories
    ) : HomeUiState()
    data object Loading : HomeUiState()
    data object Error : HomeUiState()
}