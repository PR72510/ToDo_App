package com.example.todoapp.fragments.update

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.common.BaseFragment
import com.example.todoapp.data.models.ToDoModel
import com.example.todoapp.data.viewmodel.ToDoViewModel
import com.example.todoapp.databinding.FragmentUpdateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : BaseFragment<FragmentUpdateBinding>() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val viewModel: ToDoViewModel by viewModels()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save)
            updateItem()
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = binding.etCurrentTitle.text.toString()
        val description = binding.etCurrentDescription.text.toString()
        val priority = binding.spinnerCurrentPriorities.selectedItem.toString()

        if (verifyDataFromUser(title, description)) {
            val updatedItem = ToDoModel(
                args.currentItem.id, title, parsePriority(priority), description
            )
            viewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }
}