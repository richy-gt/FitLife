package com.example.fitlife.repository

import com.example.fitlife.models.AuthResponse
import com.example.fitlife.models.LoginRequest
import com.example.fitlife.models.RegisterRequest
import com.example.fitlife.network.RetrofitClient
import android.content.Context


class AuthRepository(private val context: Context) {

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val api = RetrofitClient.getApiService(context)
            val response = api.login(LoginRequest(email, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(name: String, email: String, password: String): Result<AuthResponse> {
        return try {
            val api = RetrofitClient.getApiService(context)
            val response = api.register(RegisterRequest(name, email, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}