package com.example.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoapp.data.models.ToDoModel

/**
 * Created by PR72510 on 11/8/20.
 */

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoModel: ToDoModel)

    @Update
    suspend fun updateData(toDoModel: ToDoModel)

    @Delete
    suspend fun deleteData(toDoModel: ToDoModel)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery")
    fun searchTask(searchQuery: String): LiveData<List<ToDoModel>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<ToDoModel>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<ToDoModel>>
}