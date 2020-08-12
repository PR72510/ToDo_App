package com.example.todoapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoModel
import kotlinx.android.synthetic.main.item_todo.view.*

/**
 * Created by PR72510 on 12/8/20.
 */
class ToDoListAdapter : RecyclerView.Adapter<ToDoListAdapter.ListViewHolder>() {

    var dataList = emptyList<ToDoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        )
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.onBind(position, dataList[position])
        holder.itemView.clRowBg.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(todoData: List<ToDoModel>) {
        this.dataList = todoData
        notifyDataSetChanged()
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(position: Int, toDoModel: ToDoModel) {
            itemView.tvTitle.text = toDoModel.title
            itemView.tvDescription.text = toDoModel.description

            when (toDoModel.priority) {
                Priority.HIGH -> {
                    itemView.cvPriorityIndicator.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.red
                        )
                    )
                }
                Priority.MEDIUM -> {
                    itemView.cvPriorityIndicator.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.yellow
                        )
                    )
                }
                Priority.LOW -> {
                    itemView.cvPriorityIndicator.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.green
                        )
                    )
                }
            }
        }
    }

}