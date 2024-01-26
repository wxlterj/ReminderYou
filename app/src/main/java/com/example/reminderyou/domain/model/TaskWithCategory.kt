package com.example.reminderyou.domain.model

data class TaskWithCategory(
    val task: Task,
    val category: Category?
)