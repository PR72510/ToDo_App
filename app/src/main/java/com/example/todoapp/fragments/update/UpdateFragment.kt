package com.example.todoapp.fragments.update

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.common.BaseFragment
import com.example.todoapp.data.models.Priority
import com.example.todoapp.databinding.FragmentUpdateBinding

class UpdateFragment : BaseFragment<FragmentUpdateBinding>() {

    private val args by navArgs<UpdateFragmentArgs>()

    override fun getContentView() = R.layout.fragment_update

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpUI()
    }

    private fun setUpUI() {
        binding.etCurrentTitle.setText(args.currentItem.title)
        binding.etCurrentDescription.setText(args.currentItem.description)
        binding.spinnerCurrentPriorities.setSelection(parsePriorityToInt(args.currentItem.priority))
        binding.spinnerCurrentPriorities.onItemSelectedListener = listener
    }

    private fun parsePriorityToInt(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }
}