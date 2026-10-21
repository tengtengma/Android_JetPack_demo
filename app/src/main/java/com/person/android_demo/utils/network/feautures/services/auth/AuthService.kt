package com.person.android_demo.utils.network.feautures.services.auth

import com.person.android_demo.data.network.api.AuthAPI
import com.person.android_demo.utils.network.core.NetworkServiceProtocol
import com.person.android_demo.utils.network.feautures.models.auth.AuthResponse
import com.person.android_demo.utils.network.feautures.models.auth.LoginRequest

class AuthService(private val networkService: NetworkServiceProtocol): AuthServiceProtocol {

    override suspend fun login(username: String, password: String): Result<AuthResponse> {
        return networkService.request(AuthAPI.Login(LoginRequest(username, password)))
    }

    override suspend fun register(username: String, password: String): Result<AuthResponse> {
        return networkService.request(AuthAPI.Register(LoginRequest(username, password)))
    }
}
