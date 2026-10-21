package com.person.android_demo.utils.network.feautures.models.auth

data class LoginRequest(
    val username: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val statusCode: Int,
    val message: String,
    val data: AuthUser,
    val timestamp: String
)

data class AuthUser(
    val id: Int,
    val username: String,
    val token: String
)