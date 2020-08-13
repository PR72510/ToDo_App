package com.example.todoapp

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoModel

/**
 * Created by PR72510 on 13/8/20.
 */

@BindingAdapter("android:handleNoData")
fun handleNoDataItems(view: View, data: LiveData<List<ToDoModel>>) {
    data.value?.let {
        if (it.isEmpty())
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }
}

@BindingAdapter("android:setPriority")
fun setPriority(view: Spinner, priority: Priority) {
    when (priority) {
        Priority.HIGH -> {
            view.setSelection(0)
        }
        Priority.MEDIUM -> {
            view.setSelection(1)
        }
        Priority.LOW -> {
            view.setSelection(2)
        }
    }
}

@BindingAdapter("android:parsePriorityColor")
fun parsePriorityColor(cardView: CardView, priority: Priority) {
    when (priority) {
        Priority.HIGH -> {
            cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    cardView.context,
                    R.color.red
                )
            )
        }
        Priority.MEDIUM -> {
            cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    cardView.context,
                    R.color.yellow
                )
            )
        }
        Priority.LOW -> {
            cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    cardView.context,
                    R.color.green
                )
            )
        }
    }
}















