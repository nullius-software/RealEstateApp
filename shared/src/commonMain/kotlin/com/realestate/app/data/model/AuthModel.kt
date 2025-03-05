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
data class RegisterResponse(
    val message: String,
    val data: UserData,
    val error: String? = null
)

@Serializable
data class UserData(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val externalId: String
)

