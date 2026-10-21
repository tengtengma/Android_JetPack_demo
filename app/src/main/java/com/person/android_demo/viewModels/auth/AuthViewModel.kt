package com.person.android_demo.viewModels.auth

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.person.android_demo.models.auth.AuthModel
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.person.android_demo.utils.data.UserPreferencesManager
import com.person.android_demo.utils.network.feautures.models.auth.AuthResponse
import com.person.android_demo.utils.network.feautures.services.auth.AuthService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(private val prefsManager: UserPreferencesManager, private val authService: AuthService) : ViewModel(){

    private val authResponse: AuthResponse? = null

    var username by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoggedIn by mutableStateOf(false)

    init {
        checkLoginState()
    }
    fun checkLoginState(){
        viewModelScope.launch {
            val savedToken = prefsManager.tokenFlow.first()
            isLoggedIn = !savedToken.isNullOrBlank()
        }
    }

     fun onLoginClick() {
        if (username.isBlank() && password.isBlank()) {
            return
        }

        viewModelScope.launch {
            try {
                val result = authService.login(username, password)
                result.onSuccess { response ->
                    if (response.statusCode == 0){
                        prefsManager.saveUserInfo(response.data.token, username)
                        isLoggedIn = true
                    }else{
                        Log.e("AuthViewModel", "Login failed: ${response.message}")
                    }
                }.onFailure { error ->
                    Log.e("AuthViewModel", "Network error: ${error.localizedMessage}")
                }
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun onRegisterClick(){

        if (username.isBlank() && password.isBlank()) {
            return
        }

        viewModelScope.launch {
            try {
                val result = authService.register(username, password)
                result.onSuccess { response ->
                    if (response.statusCode == 0){
                        prefsManager.saveUserInfo(response.data.token, username)
                        isLoggedIn = true
                    }else{
                        Log.e("AuthViewModel", "Login failed: ${response.message}")
                    }
                }.onFailure { error ->
                    Log.e("AuthViewModel", "Network error: ${error.localizedMessage}")
                }
            }catch(e: Exception){
                e.printStackTrace()
            }
        }


    }

}