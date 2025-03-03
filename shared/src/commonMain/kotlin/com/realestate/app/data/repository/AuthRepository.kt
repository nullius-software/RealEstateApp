package com.realestate.app.data.repository

import com.realestate.app.data.model.LoginRequest
import com.realestate.app.data.model.LoginResponse
import com.realestate.app.data.model.RegisterRequest
import com.realestate.app.data.model.RegisterResponse
import com.realestate.app.data.remote.ApiClient
import com.realestate.app.data.remote.AuthApiClient


interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResponse
    suspend fun register(firstName: String, lastName: String, email: String, password: String): RegisterResponse
}

class AuthRepositoryImpl(private val apiClient: ApiClient) : AuthRepository {
    private val authApiClient = AuthApiClient(apiClient.client)

    override suspend fun login(email: String, password: String): LoginResponse {
        return authApiClient.login(LoginRequest(email, password))
    }

    override suspend fun register(firstName: String, lastName: String, email: String, password: String): RegisterResponse {
        return authApiClient.register(RegisterRequest(firstName, lastName, email, password))
    }
}