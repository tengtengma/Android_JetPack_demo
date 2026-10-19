package com.person.android_demo.viewModels.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.person.android_demo.models.auth.AuthModel

class AuthViewModel : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoggedIn by mutableStateOf(false)

    fun onLoginClick() {
        // 打包输入的数据
        val authData = AuthModel(
            username = username,
            password = password
        )
        println("触发登录：${authData.username}")
        // 这里后面接网络请求逻辑

        isLoggedIn = true
    }

    fun onRegisterClick(){
        // 打包输入的数据
        val authData = AuthModel(
            username = username,
            password = password
        )
        println("触发注册：${authData.username}")
        // 这里后面接网络请求逻辑
    }

}