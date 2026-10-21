package com.person.android_demo.utils.network.core

import java.lang.reflect.Type

enum class HttpMethod {
    GET, POST, PUT, DELETE
}

// 对应 Swift 的 BodyEncoding
enum class BodyEncoding {
    JSON, URL_ENCODED, NONE
}

interface NetworkRequest<T> {
    val path: String
    val method: HttpMethod
    val headers: Map<String, String>?
    val queryParameters: Map<String, String>?
    val body: Any?
    val bodyEncoding: BodyEncoding
    val responseType: Type
}
