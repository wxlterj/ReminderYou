package com.example.reminderyou.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.reminderyou.data.local.entity.CategoryEntity
import com.example.reminderyou.data.local.entity.TaskEntity

data class CategoryWithTasks(
    @Embedded
    val category: CategoryEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId",
    )
    val tasks: List<TaskEntity>
)
