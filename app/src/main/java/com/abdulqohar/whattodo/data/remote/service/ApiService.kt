package com.abdulqohar.whattodo.data.remote.service

import com.abdulqohar.whattodo.data.remote.model.AddTodoRequest
import com.abdulqohar.whattodo.data.remote.model.LoginRequest
import com.abdulqohar.whattodo.data.remote.model.LoginResponse
import com.abdulqohar.whattodo.data.remote.model.Todo
import com.abdulqohar.whattodo.data.remote.model.TodosResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse

    @GET("todos/user/{userId}")
    suspend fun getTodos(
        @Path("userId") userId: Int,
        @Header("Authorization") token: String
    ): TodosResponse

    @POST("todos/add")
    suspend fun addTodo(
        @Body request: AddTodoRequest,
        @Header("Authorization") token: String
    ): Todo
}