package com.abdulqohar.whattodo.data.remote.model

data class AddTodoRequest(
    val todo: String,
    val completed: Boolean = false,
    val userId: Int
)
