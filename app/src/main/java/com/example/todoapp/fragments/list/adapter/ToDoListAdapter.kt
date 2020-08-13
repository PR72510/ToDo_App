package com.example.todoapp.fragments.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.models.ToDoModel
import com.example.todoapp.databinding.ItemTodoBinding
import com.example.todoapp.fragments.list.ListFragmentDirections

/**
 * Created by PR72510 on 12/8/20.
 */
class ToDoListAdapter : RecyclerView.Adapter<ToDoListAdapter.ListViewHolder>() {

    var dataList = emptyList<ToDoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder.from(
            parent
        )
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun setData(todoData: List<ToDoModel>) {
        val todoDiffUtil = ToDoDiffUtil(dataList, todoData)
        val todoDiffResult = DiffUtil.calculateDiff(todoDiffUtil)
        this.dataList = todoData
//        notifyDataSetChanged()
        todoDiffResult.dispatchUpdatesTo(this)
    }

    class ListViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(toDoModel: ToDoModel) {
            binding.todo = toDoModel
            binding.adapter =
                ToDoListAdapter()
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTodoBinding.inflate(layoutInflater, parent, false)
                return ListViewHolder(
                    binding
                )
            }
        }
    }

    fun navigateToUpdate(view: View, toDoModel: ToDoModel){
        val action =
            ListFragmentDirections.actionListFragmentToUpdateFragment(
                toDoModel
            )
        view.findNavController().navigate(action)
    }
}