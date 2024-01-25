package com.example.reminderyou.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.reminderyou.data.local.dao.CategoryDao
import com.example.reminderyou.data.local.dao.TaskDao
import com.example.reminderyou.data.local.database.ReminderYouDatabase
import com.example.reminderyou.data.repository.CategoryRepository
import com.example.reminderyou.data.repository.TaskRepository
import com.example.reminderyou.domain.usecase.GetCategoriesUseCase
import com.example.reminderyou.domain.usecase.SaveCategoryUseCase
import com.example.reminderyou.domain.usecase.SaveTaskUseCase
import com.example.reminderyou.util.DATABASE_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideReminderYouDatabase(@ApplicationContext context: Context): ReminderYouDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = ReminderYouDatabase::class.java,
            name = DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: ReminderYouDatabase): TaskDao {
        return database.getTaskDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: ReminderYouDatabase): CategoryDao {
        return database.getCategoryDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepository(taskDao)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepository(categoryDao)
    }

    @Provides
    @Singleton
    fun provideSaveTaskUseCase(taskRepository: TaskRepository): SaveTaskUseCase {
        return SaveTaskUseCase(taskRepository)
    }

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(categoryRepository: CategoryRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(categoryRepository)
    }

    @Provides
    @Singleton
    fun provideSaveCategoryUseCase(categoryRepository: CategoryRepository): SaveCategoryUseCase {
        return SaveCategoryUseCase(categoryRepository)
    }
}