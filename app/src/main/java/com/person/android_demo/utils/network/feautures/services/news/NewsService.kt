package com.person.android_demo.utils.network.feautures.services.news

import com.person.android_demo.utils.network.core.NetworkServiceProtocol
import com.person.android_demo.utils.network.feautures.apis.news.NewsAPI
import com.person.android_demo.utils.network.feautures.models.news.NewsResponse

class NewsService(private val networkService: NetworkServiceProtocol) : NewsServiceProtocol  {

    override suspend fun getNews(): Result<NewsResponse> {
        return networkService.request(NewsAPI.GetNews())
    }
}
