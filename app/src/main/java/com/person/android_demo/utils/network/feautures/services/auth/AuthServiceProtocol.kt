package com.person.android_demo.utils.network.feautures.services.auth

import com.person.android_demo.utils.network.feautures.models.auth.AuthResponse

interface AuthServiceProtocol {
    suspend fun login(username: String, password: String): Result<AuthResponse>
    suspend fun register(username: String, password: String): Result<AuthResponse>
}