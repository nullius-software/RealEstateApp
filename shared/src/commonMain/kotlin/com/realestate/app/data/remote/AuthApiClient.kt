package com.realestate.app.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.realestate.app.data.model.LoginRequest
import com.realestate.app.data.model.LoginResponse
import com.realestate.app.data.model.RegisterRequest
import com.realestate.app.data.model.RegisterResponse

class AuthApiClient(private val client: HttpClient) {
    suspend fun login(request: LoginRequest): LoginResponse {
        return client.post("/api/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun register(request: RegisterRequest): RegisterResponse {
        return client.post("/api/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}