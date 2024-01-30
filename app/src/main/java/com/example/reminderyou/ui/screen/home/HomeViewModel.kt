package com.example.reminderyou.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderyou.data.mapper.toTask
import com.example.reminderyou.domain.model.Category
import com.example.reminderyou.domain.model.TaskWithCategory
import com.example.reminderyou.domain.usecase.CheckTaskUseCase
import com.example.reminderyou.domain.usecase.DeleteTaskUseCase
import com.example.reminderyou.domain.usecase.GetCategoriesUseCase
import com.example.reminderyou.domain.usecase.GetTasksWithCategoryUseCase
import com.example.reminderyou.domain.usecase.SaveCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTasksWithCategoryUseCase: GetTasksWithCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val checkTaskUseCase: CheckTaskUseCase,
    private val saveCategoryUseCase: SaveCategoryUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    var categoryNameState by mutableStateOf("")
        private set

    init {
        loadSuccessState()
    }

    fun loadSuccessState() {
        viewModelScope.launch {
            val tasks = getTasksWithCategoryUseCase()
            val categories = getCategoriesUseCase().first()

            if (_uiState.value is HomeUiState.Loading) {
                _uiState.value = HomeUiState.Success(
                    tasks = tasks,
                    categories = categories
                )
            } else {
                _uiState.update { currentState ->
                    (currentState as HomeUiState.Success).copy(
                        tasks = tasks,
                        categories = categories,
                        showTaskDetails = false
                    )
                }
            }
        }
    }

    fun changeCategoryName(name: String) {
        categoryNameState = name
    }


    fun onTaskItemClicked(currentTask: TaskWithCategory) {
        _uiState.update { currentState ->
            (currentState as HomeUiState.Success).copy(
                showTaskDetails = true,
                currentTask = currentTask
            )
        }
    }

    fun onTaskBottomSheetClosed() {
        _uiState.update { currentState ->
            (currentState as HomeUiState.Success).copy(
                showTaskDetails = false
            )
        }
    }

    fun onAddCategoryClicked() {
        _uiState.update { currentState ->
            (currentState as HomeUiState.Success).copy(
                showAddCategory = true
            )
        }
    }

    fun onAddCategoryClosed() {
        _uiState.update { currentState ->
            (currentState as HomeUiState.Success).copy(
                showAddCategory = false
            )
        }

        categoryNameState = ""
    }

    fun checkTask(task: TaskWithCategory, isChecked: Boolean) {
        val taskChecked = task.task.copy(isChecked = isChecked)
        viewModelScope.launch {
            checkTaskUseCase(taskChecked)
        }
        loadSuccessState()
    }

    fun deleteTask(taskWithCategory: TaskWithCategory) {
        viewModelScope.launch {
            deleteTaskUseCase(taskWithCategory.toTask())
        }
        loadSuccessState()
    }

    fun saveCategory() {
        viewModelScope.launch {
            saveCategoryUseCase(
                category = Category(name = categoryNameState)
            )
        }
        _uiState.update { currentState ->
            (currentState as HomeUiState.Success).copy(
                showAddCategory = false
            )
        }
        categoryNameState = ""
        loadSuccessState()
    }
}

