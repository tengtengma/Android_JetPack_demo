package com.person.android_demo.viewModels.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.person.android_demo.models.home.HomeModel
import com.person.android_demo.models.home.HomeDetailModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.person.android_demo.utils.data.UserPreferencesManager
import com.person.android_demo.utils.network.feautures.services.news.NewsService
import kotlinx.coroutines.launch

class HomeViewModel(private val prefsManager: UserPreferencesManager, private val newsService: NewsService): ViewModel() {
    var newsList = mutableStateListOf<HomeModel>()

    init {
        getNewsRequest()
    }

    fun getNewsRequest(){
        viewModelScope.launch {
            try {
                val result = newsService.getNews()
                result.onSuccess { response ->
                    if (response.statusCode == 0) {
                        newsList.clear()
                        val newItems = response.data.articles.map { article ->
                            HomeModel(
                                id = article.id,
                                author = article.author ?: "佚名",
                                title = article.title,
                                summary = article.description,
                                source = article.source,
                                imageUrl = article.image,
                                category = article.category,
                                language = article.language,
                                country = article.country,
                                publishedAt = article.publishedAt,
                                url = article.url
                            )
                        }
                        newsList.clear()
                        newsList.addAll(newItems)
                    }
                }.onFailure { error ->
                    // 💡 建议顺便处理一下失败情况（比如打印错误或提示用户）
                    println("获取新闻网络请求失败: ${error.localizedMessage}")
                }
            }catch (e: Exception){
                    e.printStackTrace()
            }
        }

    }

    fun selectedNews(homeModel: HomeModel, onNavigate: (HomeDetailModel) -> Unit){
        println("触发选中新闻: ${homeModel.title}")
        val detailModel = HomeDetailModel(
            id = homeModel.id,
            title = homeModel.title,
            url = homeModel.url
        )
        onNavigate(detailModel)
    }

    fun logout(onLogoutComplete: () -> Unit) {
        try {
            viewModelScope.launch {
                prefsManager.clear()
            }
        }catch (e: Exception){

        }finally {
            onLogoutComplete()
        }

    }
}
