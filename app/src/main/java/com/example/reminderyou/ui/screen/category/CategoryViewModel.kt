package com.example.reminderyou.ui.screen.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderyou.domain.model.Category
import com.example.reminderyou.domain.model.TaskWithCategory
import com.example.reminderyou.domain.usecase.GetCategoryUseCase
import com.example.reminderyou.domain.usecase.GetTasksWithCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject


data class CategoryUiState(
    val category: Category = Category(),
    val tasks: List<TaskWithCategory> = emptyList()
)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCategory()
    }


    private fun getCategory() {
        viewModelScope.launch {
            getCategoryUseCase(2).let {
                _uiState.update { currentState ->
                    currentState.copy(
                        category = it.second,
                        tasks = it.first
                    )
                }
            }
        }
    }

    fun pass(taks: TaskWithCategory, oe: Boolean) {
        pass(taks, oe)
    }
}