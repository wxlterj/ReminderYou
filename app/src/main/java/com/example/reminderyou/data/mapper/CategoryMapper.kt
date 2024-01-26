package com.example.reminderyou.data.mapper

import com.example.reminderyou.data.local.entity.CategoryEntity
import com.example.reminderyou.domain.model.Category

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = categoryId,
        name = this.name
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        categoryId = id,
        name = this.name
    )
}