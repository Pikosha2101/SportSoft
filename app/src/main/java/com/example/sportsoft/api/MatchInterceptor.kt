package com.example.sportsoft.api

import okhttp3.Interceptor
import okhttp3.Response

class MatchInterceptor(private val accessToken: String) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .addHeader("Token", Server().getToken())
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(newRequest)
    }

}