package com.example.todoapp.fragments.update

import android.app.AlertDialog
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
        binding?.args = args
        binding?.spinnerCurrentPriorities?.onItemSelectedListener = listener
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete '${args.currentItem.title}'")
            setMessage("Are you sure you want to Remove'${args.currentItem.title}' ?")
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteData(args.currentItem)
                Toast.makeText(
                    requireContext(),
                    "Successfully removed ${args.currentItem.title}!",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }
            setNegativeButton("No") { _, _ -> }
        }.create().show()
    }

    private fun updateItem() {
        val title = binding?.etCurrentTitle?.text.toString()
        val description = binding?.etCurrentDescription?.text.toString()
        val priority = binding?.spinnerCurrentPriorities?.selectedItem.toString()

        if (verifyDataFromUser(title, description)) {
            val updatedItem = ToDoModel(
                args.currentItem.id, title, parsePriority(priority), description
            )
            viewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(requireContext(), "Fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }
}