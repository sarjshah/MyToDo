package com.practice.mytodo.data

import androidx.room.*
import com.practice.mytodo.ui.tasks.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {


    fun getTasks(searchQuery: String, sortOrder: SortOrder, hideCompleted: Boolean):
            Flow<List<Task>> =
        when (sortOrder) {
            SortOrder.BY_DATE_CREATED -> { getTasksSortedByDateCreated(searchQuery, hideCompleted) }
            SortOrder.BY_NAME -> { getTasksSortedByName(searchQuery, hideCompleted) }
        }

    @Query("SELECT * FROM task_table WHERE (isCompleted != :hideCompleted or isCompleted = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY isImportant DESC, created")
    fun getTasksSortedByDateCreated(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE (isCompleted != :hideCompleted or isCompleted = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY isImportant DESC, name")
    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}