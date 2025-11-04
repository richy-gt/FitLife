package com.example.fitlife.network

import com.example.fitlife.models.AuthResponse
import com.example.fitlife.models.LoginRequest
import com.example.fitlife.models.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse
}