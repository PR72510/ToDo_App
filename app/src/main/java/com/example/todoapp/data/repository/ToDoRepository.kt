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

    suspend fun insertData(toDoModel: ToDoModel) {
        toDoDao.insertData(toDoModel)
    }
}