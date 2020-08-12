package com.example.todoapp.data.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoModel
import com.example.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by PR72510 on 11/8/20.
 */
class ToDoViewModel @ViewModelInject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    //    private val _data = MutableLiveData<List<ToDoModel>>()
    val data: LiveData<List<ToDoModel>>
//        get() = _data

    init {
        data = repository.getAllData()
    }

    fun insertData(todoData: ToDoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(todoData)
        }
    }
    fun updateData(todoData: ToDoModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(todoData)
        }
    }
}