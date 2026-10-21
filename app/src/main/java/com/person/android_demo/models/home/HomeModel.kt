package com.person.android_demo.models.home

data class HomeModel(
    val id: Int,
    val author: String,
    val title: String,
    val summary: String,
    val source: String,
    val imageUrl: String?,      // 在 Android 中网络图片链接通常用 String? 存储，方便 Coil 或 Glide 加载
    val category: String,
    val language: String,
    val country: String,
    val publishedAt: String,
    val url: String
)