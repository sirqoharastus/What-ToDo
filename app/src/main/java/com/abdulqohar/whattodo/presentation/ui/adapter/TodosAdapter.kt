package com.abdulqohar.whattodo.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulqohar.whattodo.data.remote.model.Todo
import com.abdulqohar.whattodo.databinding.TodoItemBinding

class TodosAdapter: RecyclerView.Adapter<TodosAdapter.TodosViewHolder>() {
    private var todosList: MutableList<Todo> = mutableListOf()
    inner class TodosViewHolder(val binding: TodoItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Todo) {
            binding.tvTask.text = item.todo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        val inflater = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
         return TodosViewHolder(inflater)
    }

    override fun getItemCount(): Int = todosList.size

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        holder.bind(todosList[position])
    }

    fun submitList(list: MutableList<Todo>) {
        todosList = list
        notifyDataSetChanged()
    }
}