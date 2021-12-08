package com.practice.myapplication.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.practice.myapplication.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class TasksViewModel @Inject constructor(taskDao: TaskDao) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    val sortOrder = MutableStateFlow(SortOrder.BY_DATE_CREATED)
    val hideCompleted = MutableStateFlow(false)

    private val tasksFlow = combine(searchQuery, sortOrder, hideCompleted) {
            searchQuery, sortOrder, hideCompleted ->
            Triple(searchQuery, sortOrder, hideCompleted)
        }.flatMapLatest { (searchQuery, sortOrder, hideCOmpleted) ->
        taskDao.getTasks(searchQuery, sortOrder, hideCOmpleted)
    }

    val tasks = tasksFlow.asLiveData()
}
enum class SortOrder { BY_NAME, BY_DATE_CREATED }