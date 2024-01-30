package com.example.reminderyou.ui.screen.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderyou.data.mapper.toTask
import com.example.reminderyou.domain.model.Category
import com.example.reminderyou.domain.model.TaskWithCategory
import com.example.reminderyou.domain.usecase.CheckTaskUseCase
import com.example.reminderyou.domain.usecase.DeleteCategoryUseCase
import com.example.reminderyou.domain.usecase.DeleteTaskUseCase
import com.example.reminderyou.domain.usecase.GetCategoryByIdUseCase
import com.example.reminderyou.domain.usecase.GetCategoryWithTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class CategoryUiState(
    val category: Category = Category(),
    val tasks: List<TaskWithCategory> = emptyList(),
    val showTaskDetails: Boolean = false,
    val currentTask:  TaskWithCategory = TaskWithCategory()
)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryWithTasksUseCase: GetCategoryWithTasksUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val checkTaskUseCase: CheckTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState = _uiState.asStateFlow()

    private val categoryId: Int = checkNotNull(savedStateHandle["categoryId"])
    init {
        getCategory()
    }


    private fun getCategory() {
        viewModelScope.launch {
            getCategoryWithTasksUseCase(categoryId).let {
                _uiState.update { currentState ->
                    currentState.copy(
                        category = it.second,
                        tasks = it.first,
                        showTaskDetails = false
                    )
                }
            }
        }
    }

    fun showTaskDetails(task: TaskWithCategory) {
        _uiState.update { currentState ->
            currentState.copy(
                showTaskDetails = true,
                currentTask = task
            )
        }
    }

    fun onTaskDetailsClosed() {
        _uiState.update { currentState ->
            currentState.copy(
                showTaskDetails = false
            )
        }
    }

    fun deleteCategory() {
        viewModelScope.launch {
            deleteCategoryUseCase(_uiState.value.category)
        }
    }

    fun checkTask(task: TaskWithCategory, isChecked: Boolean) {
        val taskChecked = task.task.copy(isChecked = isChecked)
        viewModelScope.launch {
            checkTaskUseCase(taskChecked)
            getCategory()
        }
    }

    fun deleteTask(task: TaskWithCategory = _uiState.value.currentTask) {
        viewModelScope.launch {
            deleteTaskUseCase(task.toTask())
            getCategory()
        }
    }

}