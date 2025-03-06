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
import kotlinx.coroutines.delay

class UserViewModel(private val settings: Settings) : ViewModel() {
    private val authRepository = AuthRepositoryImpl(ApiClient())
    private val _userData = MutableStateFlow<UserData?>(null)
    private val _credentials = MutableStateFlow<LoginResponse?>(null)
    private val _userEmailIsVerified = MutableStateFlow(false)
    private val _loading = MutableStateFlow(false)

    @NativeCoroutineScope
    val userData: StateFlow<UserData?> = _userData

    @NativeCoroutineScope
    val credentials: StateFlow<LoginResponse?> = _credentials

    @NativeCoroutineScope
    val userEmailIsVerified: StateFlow<Boolean> = _userEmailIsVerified

    @NativeCoroutineScope
    val loading: StateFlow<Boolean> = _loading

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
        try {
            _loading.value = true
            val response = authRepository.login(email, password)
            setCredentials(response)
            delay(5000)
            _loading.value = false
        } catch (e: Exception) {
            _loading.value = false
            throw e
        }
    }

    suspend fun register(firstName: String, lastName: String, email: String, password: String) {
        try {
            _loading.value = true
            val response = authRepository.register(firstName, lastName, email, password)
            setUserData(response.data)
            login(email, password)
            _loading.value = false
        } catch (e: Exception) {
            _loading.value = false
            throw e
        }
    }

    suspend fun checkIfUserIsVerified(): Boolean {
        _loading.value = true
        val user = userData.value

        if (user == null) {
            _loading.value = false
            throw Exception("User not found.")
        }

        val verified = authRepository.checkIfUserIsVerified(user.externalId)
        setUserEmailIsVerified(verified)

        _loading.value = false
        return verified
    }
}