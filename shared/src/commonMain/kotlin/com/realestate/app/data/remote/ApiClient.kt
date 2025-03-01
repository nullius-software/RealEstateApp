package com.realestate.app.data.remote

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.*
import io.ktor.client.plugins.defaultRequest
import kotlinx.serialization.json.Json

class ApiClient {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            url("http://10.0.2.2:8090")
        }
    }
}