package com.abdulqohar.whattodo.presentation.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulqohar.whattodo.R
import com.abdulqohar.whattodo.databinding.FragmentTodoListBinding
import com.abdulqohar.whattodo.presentation.ui.adapter.TodosAdapter
import com.abdulqohar.whattodo.presentation.ui.viewmodel.TodosViewModel
import com.abdulqohar.whattodo.util.Prefs
import com.abdulqohar.whattodo.util.Resource
import com.abdulqohar.whattodo.util.UiHelpers
import com.abdulqohar.whattodo.util.UiHelpers.Companion.dismissProgressDialog
import com.abdulqohar.whattodo.util.UiHelpers.Companion.makeSnackbar
import com.abdulqohar.whattodo.util.UiHelpers.Companion.showProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodosViewModel by viewModels()
    private lateinit var todosAdapter: TodosAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        onGetTodosStateChanged()
        initAdapter()
    }

    private fun initData() {
        lifecycleScope.launch {
            Prefs.getToken(requireContext()).collect { token ->
                Prefs.getUserId(requireContext()).collect { userId ->
                    viewModel.getTodos(userId, token)
                }
            }
        }
    }

    private fun initAdapter() {
        todosAdapter = TodosAdapter()
        binding.rvTodos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todosAdapter
        }
    }

    private fun onGetTodosStateChanged() {
        lifecycleScope.launch {
            viewModel.todosState.collect {
                when(it) {
                    is Resource.Error<*> -> {
                        dismissProgressDialog()
                        makeSnackbar(requireContext(), requireView(), it.message, true)
                    }
                    is Resource.Loading<*> -> {
                        showProgressDialog(requireActivity())
                    }
                    is Resource.Success -> {
                        dismissProgressDialog()
                        todosAdapter.submitList(it.data.todos.toMutableList())
                    }
                    else -> null
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}