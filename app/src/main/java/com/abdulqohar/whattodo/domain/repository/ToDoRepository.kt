package com.abdulqohar.whattodo.domain.repository

import com.abdulqohar.whattodo.data.remote.model.AddTodoRequest
import com.abdulqohar.whattodo.data.remote.model.LoginRequest
import com.abdulqohar.whattodo.data.remote.model.LoginResponse
import com.abdulqohar.whattodo.data.remote.model.Todo
import com.abdulqohar.whattodo.data.remote.model.TodosResponse
import com.abdulqohar.whattodo.util.Resource
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    suspend fun login(credentials: LoginRequest): Flow<Resource<LoginResponse>>

    suspend fun getTodos(userId:Int, token: String): Flow<Resource<TodosResponse>>

    suspend fun addTodos(request: AddTodoRequest, token: String): Flow<Resource<Todo>>
}