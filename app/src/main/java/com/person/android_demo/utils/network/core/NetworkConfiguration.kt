package com.person.android_demo.utils.network.core


data class NetworkConfiguration (
    val baseUrl: String,
    val requestTimeout: Long = 30L,
    val responseTimeout: Long = 60L
)
