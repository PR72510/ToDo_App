package com.example.todoapp.data

import androidx.room.TypeConverter
import com.example.todoapp.data.models.Priority

/**
 * Created by PR72510 on 11/8/20.
 */
class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String{
        return priority.name
    }

    @TypeConverter
    fun fromPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}