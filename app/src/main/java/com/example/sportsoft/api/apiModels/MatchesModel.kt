package com.example.sportsoft.api.apiModels

data class MatchesModel(
    val Token: String,
    val Authorization: String,
    val MatchSearch: MatchRequest? = null
)