package com.example.reminderyou.ui.screen.addtask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderyou.data.local.fake.DataSource
import com.example.reminderyou.domain.model.Category
import com.example.reminderyou.domain.usecase.GetCategoriesUseCase
import com.example.reminderyou.domain.usecase.SaveCategoryUseCase
import com.example.reminderyou.util.dateFormatter
import com.example.reminderyou.util.timeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

data class AddTaskUiState(
    val taskTitle: String = "",
    val taskDescription: String = "",
    val datePicked: String = "",
    val timePicked: String = "",
    val categories: List<Category> = emptyList(),
    val showCategoryMenu: Boolean = false,
    val showAddCategory: Boolean = false,
)

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val saveCategoryUseCase: SaveCategoryUseCase
) : ViewModel() {

    var titleState by mutableStateOf("")
        private set

    var descriptionState by mutableStateOf("")
        private set

    var categoryName by mutableStateOf("")
        private set


    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState = _uiState.asStateFlow()


    fun changeTitle(title: String) {
        titleState = title
        _uiState.update { it.copy(taskTitle = titleState) }
    }

    fun changeDescription(description: String) {
        descriptionState = description
        _uiState.update { it.copy(taskDescription = descriptionState) }
    }

    fun updateDatePicked(date: Long) {
        val datePicked = Instant.ofEpochMilli(date)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        _uiState.update {
            it.copy(
                datePicked = datePicked.format(dateFormatter)
            )
        }
    }

    fun updateTimePicked(hour: Int, minute: Int) {
        val timePicked = LocalTime.of(hour, minute)
        _uiState.update {
            it.copy(
                timePicked = timePicked.format(timeFormatter)
            )
        }
    }

    fun showCategories(menuExpanded: Boolean) {
        if (menuExpanded) {
            viewModelScope.launch {
                getCategoriesUseCase().collectLatest { categories ->
                    _uiState.update { it.copy(categories = categories) }
                }
            }
        }
        _uiState.update { it.copy(showCategoryMenu = menuExpanded) }
    }

    fun showAddCategory() {
        _uiState.update { it.copy(showAddCategory = true, showCategoryMenu = false) }
    }

    fun closeAddCategory() {
        _uiState.update { it.copy(showAddCategory = false) }
    }

    fun changeCategoryName(name: String) {
        categoryName = name
    }

    fun saveCategory() {
        viewModelScope.launch {
            saveCategoryUseCase(
                category = Category(name = categoryName)
            )
        }
        _uiState.update { it.copy(showAddCategory = false) }
    }
}

