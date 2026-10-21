package com.person.android_demo.utils.network.core

sealed class NetworkError : Exception() {
    class NetworkConnectionError : NetworkError()
    data class HttpError(val code: Int, override val message: String?) : NetworkError()
    class Unauthorized : NetworkError()
    class NotFound : NetworkError()
    data class DecodingError(override val cause: Throwable?) : NetworkError()
    data class Unknown(override val cause: Throwable?) : NetworkError()
}