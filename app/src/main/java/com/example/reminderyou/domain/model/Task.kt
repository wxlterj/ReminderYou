package com.example.reminderyou.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Task (
    val id: Int = 0,
    val title: String = "",
    val description: String? = null,
    val date: LocalDate = LocalDate.now(),
    val hour: LocalTime = LocalTime.now(),
    val category: Category = Category(),
    val isChecked: Boolean = false,
)