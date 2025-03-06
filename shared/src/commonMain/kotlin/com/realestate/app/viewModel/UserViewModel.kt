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
    private val _userEmailIsVerified = MutableStateFlow(false)

    @NativeCoroutineScope
    val userData: StateFlow<UserData?> = _userData

    @NativeCoroutineScope
    val credentials: StateFlow<LoginResponse?> = _credentials

    @NativeCoroutineScope
    val userEmailIsVerified: StateFlow<Boolean> = _userEmailIsVerified

    private fun setUserData(data: UserData) {
        _userData.value = data
    }

    private fun setCredentials(loginResponse: LoginResponse) {
        _credentials.value = loginResponse
        settings.putString("accessToken", loginResponse.accessToken)
        settings.putString("refreshToken", loginResponse.refreshToken)
    }

    private fun setUserEmailIsVerified(isVerified: Boolean) {
        _userEmailIsVerified.value = isVerified
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

    suspend fun register(firstName: String, lastName: String, email: String, password: String) {
        val response = authRepository.register(firstName, lastName, email, password)
        setUserData(response.data)
        login(email, password)
    }

    suspend fun checkIfUserIsVerified(): Boolean {
        val user = userData.value ?: throw Exception("User not found.")
        val verified = authRepository.checkIfUserIsVerified(user.externalId)
        setUserEmailIsVerified(verified)
        return verified
    }
}