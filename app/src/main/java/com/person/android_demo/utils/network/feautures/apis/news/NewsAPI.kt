package com.person.android_demo.utils.network.feautures.apis.news

import com.person.android_demo.utils.network.core.BodyEncoding
import com.person.android_demo.utils.network.core.HttpMethod
import com.person.android_demo.utils.network.core.NetworkRequest
import com.person.android_demo.utils.network.feautures.models.news.NewsResponse
import java.lang.reflect.Type

sealed class NewsAPI<T>(override val responseType: Type) : NetworkRequest<T> {

    data class GetNews(val category: String = "") : NewsAPI<NewsResponse>(NewsResponse::class.java) {
        override val path: String = "/api/News"
    }

    override val method: HttpMethod
        get() = HttpMethod.GET

    override val queryParameters: Map<String, String>?
        get() = null

    override val headers: Map<String, String>?
        get() = null

    override val body: Any?
        get() = null

    override val bodyEncoding: BodyEncoding
        get() = BodyEncoding.NONE
}
