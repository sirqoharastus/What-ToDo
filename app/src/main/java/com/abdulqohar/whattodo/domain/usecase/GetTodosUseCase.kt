package com.abdulqohar.whattodo.domain.usecase

import com.abdulqohar.whattodo.data.remote.model.TodosResponse
import com.abdulqohar.whattodo.domain.repository.ToDoRepository
import com.abdulqohar.whattodo.util.Resource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTodosUseCase @Inject constructor(private val repository: ToDoRepository) {

    suspend operator fun invoke(userId: Int, token: String): Flow<Resource<TodosResponse>> =
        repository.getTodos(userId, token)
}