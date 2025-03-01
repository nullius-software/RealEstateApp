package com.realestate.app.viewModel

object Validator {

    fun validateCommon(input: String): ValidationResult {
        return when {
            input.count { it.isLetter() } < 3 -> {
                ValidationResult.Invalid("Input must contain at least 3 letters")
            }
            else -> {
                ValidationResult.Valid
            }
        }
    }

    fun validateEmail(email: String): ValidationResult {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return when {
            email.isBlank() -> {
                ValidationResult.Invalid("Email cannot be empty")
            }
            !emailRegex.matches(email) -> {
                ValidationResult.Invalid("Invalid email format")
            }
            else -> {
                ValidationResult.Valid
            }
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> {
                ValidationResult.Invalid("Password cannot be empty")
            }
            password.length < 8 -> {
                ValidationResult.Invalid("Password must be at least 8 characters")
            }
            !password.any { it.isDigit() } -> {
                ValidationResult.Invalid("Password must contain at least one digit")
            }
            !password.any { it.isUpperCase() } -> {
                ValidationResult.Invalid("Password must contain at least one uppercase letter")
            }
            !password.any { it.isLowerCase() } -> {
                ValidationResult.Invalid("Password must contain at least one lowercase letter")
            }
            !password.any { !it.isLetterOrDigit() } -> {
                ValidationResult.Invalid("Password must contain at least one special character")
            }
            else -> {
                ValidationResult.Valid
            }
        }
    }
}

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val error: String) : ValidationResult()
}