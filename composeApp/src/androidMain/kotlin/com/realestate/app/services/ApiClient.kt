package com.realestate.app.services

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import com.realestate.app.models.LoginRequest
import com.realestate.app.models.LoginResponse
import com.realestate.app.models.RegisterRequest
import com.realestate.app.models.RegisterResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    private val baseUrl = "http://localhost:8090/api"

    suspend fun login(username: String, password: String): LoginResponse {
        return client.post("$baseUrl/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(username, password))
        }.body()
    }

    suspend fun register(firstName: String, lastName: String, email: String, password: String): RegisterResponse {
        return client.post("$baseUrl/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(RegisterRequest(firstName, lastName, email, password))
        }.body()
    }

    suspend fun getProperties(): List<Any> {
        return client.get("$baseUrl/estate").body()
    }


}