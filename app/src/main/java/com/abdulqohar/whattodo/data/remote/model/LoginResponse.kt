package com.abdulqohar.whattodo.data.remote.model

data class LoginResponse(
    val id: Int,
    val token: String,
    val username: String
)
