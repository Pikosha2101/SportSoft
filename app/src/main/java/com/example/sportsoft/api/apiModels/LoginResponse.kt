package com.example.sportsoft.api.apiModels

data class LoginResponse(
    val success: Boolean,
    val code: Int,
    val error: String?,
    val token: String?
)
