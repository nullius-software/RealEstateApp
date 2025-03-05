package com.realestate.app.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.realestate.app.data.model.LoginRequest
import com.realestate.app.data.model.LoginResponse
import com.realestate.app.data.model.RegisterRequest
import com.realestate.app.data.model.RegisterResponse
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException

class AuthApiClient(private val client: HttpClient) {
    suspend fun login(request: LoginRequest): LoginResponse {
        return client.post("/api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
    }

    suspend fun checkIfUserIsVerified(externalUserId: String): Boolean {
        return client.get("/api/auth/user/${externalUserId}/is-verified").body()
    }

    suspend fun register(request: RegisterRequest): RegisterResponse {
        return try {
            val response = client.post("/api/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            when (response.status.value) {
                in 200..299 -> response.body()
                in 400..499 -> throw ClientRequestException(response, "${response.status.value}")
                in 500..599 -> throw ServerResponseException(response, "${response.status.value}")
                else -> throw Exception("${response.status.value}")
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }


}