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
import com.person.android_demo.ui.theme.Android_demoTheme
import com.person.android_demo.viewModels.auth.AuthViewModel
import com.person.android_demo.viewModels.home.HomeViewModel
import com.person.android_demo.views.auth.AuthView
import com.person.android_demo.views.home.HomeView

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_demoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    if(authViewModel.isLoggedIn){
                        HomeView(
                            viewModel = homeViewModel,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }else{
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Android_demoTheme {
        AuthView(viewModel = AuthViewModel())
    }
}