package com.example.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by PR72510 on 11/8/20.
 */

@Entity(tableName = "todo_table")
data class ToDoModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
)