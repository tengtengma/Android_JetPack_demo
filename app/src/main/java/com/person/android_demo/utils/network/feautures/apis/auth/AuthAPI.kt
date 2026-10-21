package com.person.android_demo.data.network.api

import com.person.android_demo.utils.network.core.BodyEncoding
import com.person.android_demo.utils.network.core.HttpMethod
import com.person.android_demo.utils.network.core.NetworkRequest
import com.person.android_demo.utils.network.feautures.models.auth.AuthResponse
import com.person.android_demo.utils.network.feautures.models.auth.LoginRequest
import java.lang.reflect.Type

sealed class AuthAPI<T>(override val responseType: Type) : NetworkRequest<T> {

    data class Login(val request: LoginRequest) : AuthAPI<AuthResponse>(AuthResponse::class.java) {
        override val path: String = "/api/Auth/login"
    }

    data class Register(val request: LoginRequest) : AuthAPI<AuthResponse>(AuthResponse::class.java) {
        override val path: String = "/api/Auth/register"
    }

    override val method: HttpMethod
        get() = HttpMethod.POST

    override val queryParameters: Map<String, String>?
        get() = null

    override val headers: Map<String, String>?
        get() = null

    override val body: Any?
        get() = when (this) {
            is Login -> request
            is Register -> request
        }

    override val bodyEncoding: BodyEncoding
        get() = BodyEncoding.JSON
}
