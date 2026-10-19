package com.person.android_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.person.android_demo.ui.theme.Android_demoTheme
import com.person.android_demo.viewModels.auth.AuthViewModel
import com.person.android_demo.viewModels.home.HomeDetailViewModel
import com.person.android_demo.viewModels.home.HomeViewModel
import com.person.android_demo.views.auth.AuthView
import com.person.android_demo.views.home.HomeDetailView
import com.person.android_demo.views.home.HomeView
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_demoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    if (authViewModel.isLoggedIn) {
                        HomeNavHost(
                            onLogout = { authViewModel.isLoggedIn = false },
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
fun HomeNavHost( onLogout: () -> Unit, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home_list", modifier = modifier) {
        composable("home_list") {
            val homeViewModel: HomeViewModel = viewModel()
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
    Android_demoTheme {
        AuthView(viewModel = AuthViewModel())
    }
}
