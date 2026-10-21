package com.person.android_demo.utils.network.di

import android.app.Application
import com.person.android_demo.utils.data.UserPreferencesManager
import com.person.android_demo.utils.network.core.NetworkConfiguration
import com.person.android_demo.utils.network.core.NetworkManager
import com.person.android_demo.utils.network.feautures.services.auth.AuthService
import com.person.android_demo.utils.network.feautures.services.news.NewsService
import com.person.android_demo.viewModels.auth.AuthViewModel
import com.person.android_demo.viewModels.home.HomeViewModel

class AppContainer(private val application: Application, val configuration: NetworkConfiguration) {
    val networkService = NetworkManager(configuration)

    val prefsManager = UserPreferencesManager(application)
    val authService = AuthService(networkService)
    val newsService = NewsService(networkService)

    val authViewModel by lazy {
        AuthViewModel(
            prefsManager = prefsManager,
            authService = authService
        )
    }

    val homeViewModel by lazy{
        HomeViewModel(
            prefsManager = prefsManager,
            newsService = newsService
        )
    }
}