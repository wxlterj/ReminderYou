package com.example.reminderyou.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.reminderyou.data.local.dao.TaskDao
import com.example.reminderyou.data.local.database.ReminderYouDatabase
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
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: ReminderYouDatabase): TaskDao {
        return database.getTaskDao()
    }
}