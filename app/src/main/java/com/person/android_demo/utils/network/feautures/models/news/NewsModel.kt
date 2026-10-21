package com.person.android_demo.utils.network.feautures.models.news

class NewsRequest

data class NewsResponse(
    val statusCode: Int,
    val message: String,
    val data: NewsData,
    val timestamp: String
)

data class NewsData(
    val total: Int,
    val articles: List<NewsArticle>
)

data class NewsArticle(
    val id: Int,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val source: String,
    val image: String?,
    val category: String,
    val language: String,
    val country: String,
    val publishedAt: String
)