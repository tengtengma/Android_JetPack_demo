package com.person.android_demo.utils.network.core

interface NetworkServiceProtocol {
    suspend fun <T> request(request: NetworkRequest<T>): Result<T>
}