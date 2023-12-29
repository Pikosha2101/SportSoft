package com.example.sportsoft.API

import com.example.sportsoft.API.ApiModels.LoginRequest
import com.example.sportsoft.API.ApiModels.LoginResponse
import com.example.sportsoft.API.ApiModels.MatchResponse
import com.example.sportsoft.API.ApiModels.MatchesModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("api/login")
    fun login(): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("api/match")
    fun getMatches(@Query("MatchSearch[sinceDt]") test : String?, @Query("MatchSearch[toDt]") test1 : String?): Call<MatchResponse>


    @Headers("Content-Type: application/json")
    @POST("api/match")
    fun getMatches(): Call<MatchResponse>
}