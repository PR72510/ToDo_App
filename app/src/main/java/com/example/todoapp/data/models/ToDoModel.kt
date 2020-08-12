package com.example.todoapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.models.Priority
import kotlinx.android.parcel.Parcelize

/**
 * Created by PR72510 on 11/8/20.
 */

@Entity(tableName = "todo_table")
@Parcelize
data class ToDoModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
): Parcelable