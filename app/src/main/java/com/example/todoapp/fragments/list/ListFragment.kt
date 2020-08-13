package com.example.todoapp.fragments.list

import android.animation.LayoutTransition
import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoapp.R
import com.example.todoapp.common.BaseFragment
import com.example.todoapp.data.models.ToDoModel
import com.example.todoapp.data.viewmodel.ToDoViewModel
import com.example.todoapp.databinding.FragmentListBinding
import com.example.todoapp.fragments.list.adapter.ToDoListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(), SearchView.OnQueryTextListener {

    private val viewModel: ToDoViewModel by viewModels()
    private val adapter: ToDoListAdapter by lazy { ToDoListAdapter() }

    override fun getContentView(): Int = R.layout.fragment_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        setUpRecyclerView()
        setUpListeners()
        handleObservers()
    }

    private fun setUpListeners() {
        binding?.fabAddTask?.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToAddFragment()
            findNavController().navigate(action)
        }
    }

    private fun handleObservers() {
        viewModel.data.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
        })
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding?.rvTasks
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView?.itemAnimator = SlideInUpAnimator().apply { addDuration = 300 }

        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView?) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                viewModel.deleteData(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                restoreDeletedItem(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedItem(view: View, deletedItem: ToDoModel) {
        val snackbar = Snackbar.make(
            view, "Deleted ${deletedItem.title}", Snackbar.LENGTH_LONG
        )
        snackbar.setAction(getString(R.string.undo)) {
            viewModel.insertData(deletedItem)
        }
        snackbar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        val searchBar = searchView.findViewById<LinearLayout>(R.id.search_bar)
        searchBar.layoutTransition = LayoutTransition()

        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> confirmRemoveAll()
            R.id.low_priority -> viewModel.sortedByLowPriority.observe(viewLifecycleOwner, Observer { adapter.setData(it) })
            R.id.high_priority -> viewModel.sortedByHighPriority.observe(viewLifecycleOwner, Observer { adapter.setData(it) })
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoveAll() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete All Tasks")
            setMessage("Are you sure you want to remove all tasks ?")
            setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAll()
                Toast.makeText(
                    requireContext(),
                    "Successfully removed all tasks",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNegativeButton("No") { _, _ -> }
        }.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searDatabase(query)
        }
        return true
    }

    private fun searDatabase(query: String) {
        val searchQuery = "%$query%"

        viewModel.searchDatabse(searchQuery).observe(viewLifecycleOwner, Observer { list ->
            list?.let { adapter.setData(list) }
        })
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searDatabase(query)
        }
        return true
    }
}