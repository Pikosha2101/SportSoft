package com.example.sportsoft.api.apiModels

data class MatchResponse(
    val success: Boolean,
    val code: Int,
    val error: String?,
    val matches: List<MatchInfo>?
)
