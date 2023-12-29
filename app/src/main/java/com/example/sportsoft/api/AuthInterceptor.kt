package com.example.sportsoft.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val basic64: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Token", Server().getToken())
            .header(
                "Authorization",
                "Basic $basic64"
            )
            .build()

        return chain.proceed(newRequest)
    }
}