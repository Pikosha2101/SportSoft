package com.example.sportsoft.API

data class LoginResponse(
    val success: Boolean,
    val code: Int,
    val error: String?,
    val token: String?
)
