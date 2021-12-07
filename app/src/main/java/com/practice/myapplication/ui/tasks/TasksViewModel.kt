package com.practice.myapplication.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.practice.myapplication.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(taskDao: TaskDao) : ViewModel() {
    val tasks = taskDao.fetchTasks().asLiveData()
}