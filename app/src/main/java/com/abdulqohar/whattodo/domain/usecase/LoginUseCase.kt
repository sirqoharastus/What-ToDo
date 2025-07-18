package com.abdulqohar.whattodo.domain.usecase

import com.abdulqohar.whattodo.data.remote.model.LoginRequest
import com.abdulqohar.whattodo.data.remote.model.LoginResponse
import com.abdulqohar.whattodo.domain.repository.ToDoRepository
import com.abdulqohar.whattodo.util.Resource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class LoginUseCase @Inject constructor(private val repository: ToDoRepository) {
    suspend operator fun invoke(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> =
        repository.login(loginRequest)
}