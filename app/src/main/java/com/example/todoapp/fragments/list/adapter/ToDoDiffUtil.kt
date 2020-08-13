package com.example.todoapp.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.todoapp.data.models.ToDoModel

/**
 * Created by PR72510 on 13/8/20.
 */
class ToDoDiffUtil(
    private val oldList: List<ToDoModel>,
    private val newList: List<ToDoModel>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].title == newList[newItemPosition].title
                && oldList[oldItemPosition].priority == newList[newItemPosition].priority
                && oldList[oldItemPosition].description == newList[newItemPosition].description
    }
}