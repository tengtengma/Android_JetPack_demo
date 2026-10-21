package com.person.android_demo.utils.network.feautures.services.news

import com.person.android_demo.utils.network.feautures.models.news.NewsResponse


interface NewsServiceProtocol {
    suspend fun getNews() : Result<NewsResponse>
}