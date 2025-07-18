package com.abdulqohar.whattodo.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulqohar.whattodo.data.remote.model.TodosResponse
import com.abdulqohar.whattodo.domain.usecase.GetTodosUseCase
import com.abdulqohar.whattodo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TodosViewModel @Inject constructor(private val getTodosUseCase: GetTodosUseCase): ViewModel() {

    private var _todosState: MutableStateFlow<Resource<TodosResponse>?> = MutableStateFlow(null)
    val todosState: StateFlow<Resource<TodosResponse>?> get() = _todosState

    fun getTodos(userId: Int, token: String) {
        viewModelScope.launch {
            getTodosUseCase(userId, token).collect {
                _todosState.emit(it)
            }
        }
    }
}