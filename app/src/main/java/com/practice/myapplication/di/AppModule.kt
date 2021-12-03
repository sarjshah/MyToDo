package com.practice.myapplication.di

import android.app.Application
import androidx.room.Room
import com.practice.myapplication.data.TaskDao
import com.practice.myapplication.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): TaskDatabase =
        Room.databaseBuilder(app, TaskDatabase::class.java, "task_database")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun provideTaskDao(db: TaskDatabase): TaskDao = db.taskDao()
}