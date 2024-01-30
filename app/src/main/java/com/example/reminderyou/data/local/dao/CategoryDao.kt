package com.example.reminderyou.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.reminderyou.data.local.entity.CategoryEntity
import com.example.reminderyou.data.local.entity.relations.CategoryWithTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<CategoryWithTasks>>

    @Transaction
    @Query("SELECT * FROM categories WHERE categoryId = :categoryId")
    fun getCategoryWithTasksById(categoryId: Int): Flow<CategoryWithTasks>

    @Query("SELECT * FROM categories WHERE categoryId = :categoryId")
    fun getCategoryById(categoryId: Int): Flow<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategory(categoryEntity: CategoryEntity): Long

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity)
}