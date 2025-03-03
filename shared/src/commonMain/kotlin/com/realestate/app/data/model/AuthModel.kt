package com.realestate.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String?,
    val error: String? = null
)

@Serializable
data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

@Serializable
data class RegisterData(
    val email: String,
    val firstName: String,
    val lastName: String,
)

@Serializable
data class RegisterResponse(
    val message: String,
    val data: RegisterData,
    val error: String? = null
)