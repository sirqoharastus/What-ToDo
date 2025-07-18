package com.abdulqohar.whattodo.data.repository

import com.abdulqohar.whattodo.data.remote.model.AddTodoRequest
import com.abdulqohar.whattodo.data.remote.model.LoginRequest
import com.abdulqohar.whattodo.data.remote.model.LoginResponse
import com.abdulqohar.whattodo.data.remote.model.Todo
import com.abdulqohar.whattodo.data.remote.model.TodosResponse
import com.abdulqohar.whattodo.data.remote.service.ApiService
import com.abdulqohar.whattodo.domain.repository.ToDoRepository
import com.abdulqohar.whattodo.util.ApiCallsHandler
import com.abdulqohar.whattodo.util.Resource
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ToDoRepositoryImpl @Inject constructor( private val api: ApiService): ToDoRepository {
    override suspend fun login(credentials: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading())
        emit(
            ApiCallsHandler.safeApiCall(Dispatchers.IO) {
                api.login(credentials)
            }
        )
    }

    override suspend fun getTodos(userId: Int, token: String): Flow<Resource<TodosResponse>> = flow {
        emit(Resource.Loading())
        emit(
            ApiCallsHandler.safeApiCall(Dispatchers.IO) {
                api.getTodos(userId, token)
            }
        )
    }

    override suspend fun addTodos(request: AddTodoRequest, token: String): Flow<Resource<Todo>> {
        TODO("Not yet implemented")
    }
}