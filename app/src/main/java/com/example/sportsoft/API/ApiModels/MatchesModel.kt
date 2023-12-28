package com.example.sportsoft.API.ApiModels

data class MatchesModel(
    val Token: String,
    val Authorization: String,
    val MatchSearch: MatchRequest? = null
)