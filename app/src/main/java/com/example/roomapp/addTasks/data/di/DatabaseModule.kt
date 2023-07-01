package com.example.roomapp.addTasks.data.di

import android.content.Context
import androidx.room.Room
import com.example.roomapp.addTasks.data.TaskDao
import com.example.roomapp.addTasks.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideTasksDao(taskDatabase: TaskDatabase):TaskDao{
        return taskDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskDatabase(@ApplicationContext appContext: Context): TaskDatabase {
        return Room.databaseBuilder(
            appContext, TaskDatabase::
            class.java, "TaskDatanase"
        ).build()
    }
}