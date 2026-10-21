package com.person.android_demo.utils.network.core

data class BaseResponse(
    val statusCode: Int,
    val message: String,
    val data: Any
)
