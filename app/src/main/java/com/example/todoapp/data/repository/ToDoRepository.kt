package com.example.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.example.todoapp.data.ToDoDao
import com.example.todoapp.data.models.ToDoModel
import javax.inject.Inject

/**
 * Created by PR72510 on 11/8/20.
 */
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {

    fun getAllData(): LiveData<List<ToDoModel>> = toDoDao.getAllData()
    val sortByHighPriority = toDoDao.sortByHighPriority()
    val sortByLowPriority = toDoDao.sortByLowPriority()

    suspend fun insertData(toDoModel: ToDoModel) {
        toDoDao.insertData(toDoModel)
    }

    suspend fun updateData(toDoModel: ToDoModel) {
        toDoDao.updateData(toDoModel)
    }

    suspend fun deleteData(toDoModel: ToDoModel) {
        toDoDao.deleteData(toDoModel)
    }

    suspend fun deleteAll() = toDoDao.deleteAll()

    fun searchTask(searchQuery: String) = toDoDao.searchTask(searchQuery)
}