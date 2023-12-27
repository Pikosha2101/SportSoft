package com.example.sportsoft.API.ApiModels

data class MatchResponse(
    val success: Boolean,
    val code: Int,
    val error: String?,
    val matches: List<MatchInfo>?
)