package com.example.sportsoft.API

import com.example.sportsoft.API.ApiModels.LoginRequest
import com.example.sportsoft.API.ApiModels.LoginResponse
import com.example.sportsoft.API.ApiModels.MatchResponse
import com.example.sportsoft.API.ApiModels.MatchesModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("api/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("api/match")
    fun getMatches(@Body request: MatchesModel): Call<MatchResponse>
}