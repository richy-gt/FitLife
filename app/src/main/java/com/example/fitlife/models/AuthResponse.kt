package com.example.fitlife.models

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val token: String?,
    val user: User?
)

data class User(
    val id: Int,
    val name: String,
    val email: String
)