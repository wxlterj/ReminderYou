package com.example.reminderyou.domain.model

data class TaskWithCategory(
    val task: Task = Task(),
    val category: Category? = null
)