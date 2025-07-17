package com.abdulqohar.whattodo.data.repository

import com.abdulqohar.whattodo.data.remote.model.AddTodoRequest
import com.abdulqohar.whattodo.data.remote.model.LoginRequest
import com.abdulqohar.whattodo.data.remote.model.LoginResponse
import com.abdulqohar.whattodo.data.remote.model.Todo
import com.abdulqohar.whattodo.data.remote.model.TodosResponse
import com.abdulqohar.whattodo.domain.repository.ToDoRepository
import com.abdulqohar.whattodo.util.Resource
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl: ToDoRepository {
    override suspend fun login(credentials: LoginRequest): Flow<Resource<LoginResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTodos(userId: Int, token: String): Flow<Resource<TodosResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun addTodos(request: AddTodoRequest, token: String): Flow<Resource<Todo>> {
        TODO("Not yet implemented")
    }
}