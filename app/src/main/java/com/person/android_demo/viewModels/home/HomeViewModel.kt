package com.person.android_demo.viewModels.home

import androidx.compose.runtime.mutableStateListOf
import com.person.android_demo.models.home.HomeModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    var newsList = mutableStateListOf<HomeModel>()

    init {
        getNewsRequest()
    }
    fun getNewsRequest(){
        //发送请求获取数据 调用接口
        newsList.add(
            HomeModel(
                id = "1",
                author = "管理员",
                title = "Android 开发实战",
                summary = "Jetpack Compose 与 MVVM 架构指南...",
                source = "Android News",
                imageUrl = "https://picsum.photos/400/200?random=1",
                category = "Tech",
                language = "zh",
                country = "CN",
                publishedAt = "2026-10-19",
                url = "https://developer.android.com"
            )
        )
    }
}