package com.realestate.app.viewModel

import androidx.lifecycle.ViewModel
import com.realestate.app.data.model.LoginResponse
import com.realestate.app.data.model.UserData
import com.realestate.app.data.remote.ApiClient
import com.realestate.app.data.repository.AuthRepositoryImpl
import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import com.russhwolf.settings.Settings

class UserViewModel(private val settings: Settings) : ViewModel() {
    private val authRepository = AuthRepositoryImpl(ApiClient())
    private val _userData = MutableStateFlow<UserData?>(null)
    private val _credentials = MutableStateFlow<LoginResponse?>(null)

    @NativeCoroutineScope
    val userData: StateFlow<UserData?> = _userData

    @NativeCoroutineScope
    val credentials: StateFlow<LoginResponse?> = _credentials

    fun setUserData(data: UserData) {
        _userData.value = data
    }

    private fun setCredentials(loginResponse: LoginResponse) {
        _credentials.value = loginResponse
        settings.putString("accessToken", loginResponse.accessToken)
        settings.putString("refreshToken", loginResponse.refreshToken)
    }

    fun loadCredentials() {
        val accessToken = settings.getStringOrNull("accessToken")
        val refreshToken = settings.getStringOrNull("refreshToken")
        if (accessToken != null && refreshToken != null) {
            _credentials.value = LoginResponse(accessToken, refreshToken)
        }
    }

    suspend fun login(email: String, password: String) {
        val response = authRepository.login(email, password)
        setCredentials(response)
    }
}