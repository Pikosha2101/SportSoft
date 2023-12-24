package com.example.sportsoft.API.ApiModels

data class LoginResponse(
    val success: Boolean,
    val code: Int,
    val error: String?,
    val token: String?
)
