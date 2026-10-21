package com.person.android_demo

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.person.android_demo.ui.theme.Android_demoTheme
import com.person.android_demo.utils.data.UserPreferencesManager
import com.person.android_demo.utils.network.core.NetworkConfiguration
import com.person.android_demo.utils.network.di.AppContainer
import com.person.android_demo.utils.network.feautures.services.auth.AuthService
import com.person.android_demo.viewModels.auth.AuthViewModel
import com.person.android_demo.viewModels.home.HomeDetailViewModel
import com.person.android_demo.viewModels.home.HomeViewModel
import com.person.android_demo.views.auth.AuthView
import com.person.android_demo.views.home.HomeDetailView
import com.person.android_demo.views.home.HomeView
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {

    //lateinit延迟初始化 只是暂时为空 后续肯定不为空
    lateinit var container: AppContainer
        private set
    private val authViewModel: AuthViewModel by lazy {
        container.authViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val  config = NetworkConfiguration(
            baseUrl = "http://192.168.68.55:5177",
            requestTimeout = 30L,
            responseTimeout = 60L
        )
        container = AppContainer(application = application, configuration = config)

        enableEdgeToEdge()
        setContent {
            Android_demoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    if (authViewModel.isLoggedIn) {
                        HomeNavHost(
                            container = container,
                            onLogout = {
                                container.homeViewModel.logout {
                                    authViewModel.isLoggedIn = false // 这里的回调只负责最终的路由切换/状态标记
                                }
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        AuthView(
                            viewModel = authViewModel,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeNavHost(container: AppContainer, onLogout: () -> Unit, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home_list", modifier = modifier) {
        composable("home_list") {
            //val homeViewModel: HomeViewModel = viewModel()
            val homeViewModel: HomeViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        HomeViewModel(
                            prefsManager = container.prefsManager,
                            newsService = container.newsService
                        )
                    }
                }
            )
            HomeView(
                viewModel = homeViewModel,
                onItemClick = { detailModel ->
                    val encodedTitle = URLEncoder.encode(detailModel.title, StandardCharsets.UTF_8.name())
                    val encodedUrl = URLEncoder.encode(detailModel.url, StandardCharsets.UTF_8.name())
                    navController.navigate("home_detail?id=${detailModel.id}&title=$encodedTitle&url=$encodedUrl")
                },
                onLogout = onLogout
            )
        }
        composable("home_detail?id={id}&title={title}&url={url}") { backStackEntry ->

            val detailViewModel: HomeDetailViewModel = viewModel()
            HomeDetailView(
                viewModel = detailViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val context = LocalContext.current.applicationContext as? Application
        ?: Application()
    Android_demoTheme {
        AuthView(viewModel = AuthViewModel(null as UserPreferencesManager, null as Any as AuthService))
    }
}
