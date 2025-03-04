package com.realestate.app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RegisterViewModel : ViewModel() {
    private val _firstName = MutableStateFlow("")
    private val _lastName = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _confirmPassword = MutableStateFlow("")

    private val _firstNameBlurred = MutableStateFlow(false)
    private val _lastNameBlurred = MutableStateFlow(false)
    private val _emailBlurred = MutableStateFlow(false)
    private val _passwordBlurred = MutableStateFlow(false)
    private val _confirmPasswordBlurred = MutableStateFlow(false)

    private val _passwordVisible = MutableStateFlow(false)
    private val _confirmPasswordVisible = MutableStateFlow(false)

    @NativeCoroutinesState
    val firstName: StateFlow<String> = _firstName

    @NativeCoroutinesState
    val lastName: StateFlow<String> = _lastName

    @NativeCoroutinesState
    val email: StateFlow<String> = _email

    @NativeCoroutinesState
    val password: StateFlow<String> = _password

    @NativeCoroutinesState
    val confirmPassword: StateFlow<String> = _confirmPassword

    @NativeCoroutinesState
    val firstNameBlurred: StateFlow<Boolean> = _firstNameBlurred

    @NativeCoroutinesState
    val lastNameBlurred: StateFlow<Boolean> = _lastNameBlurred

    @NativeCoroutinesState
    val emailBlurred: StateFlow<Boolean> = _emailBlurred

    @NativeCoroutinesState
    val passwordBlurred: StateFlow<Boolean> = _passwordBlurred

    @NativeCoroutinesState
    val confirmPasswordBlurred: StateFlow<Boolean> = _confirmPasswordBlurred

    @NativeCoroutinesState
    val passwordVisible: StateFlow<Boolean> = _passwordVisible

    @NativeCoroutinesState
    val confirmPasswordVisible: StateFlow<Boolean> = _confirmPasswordVisible

    @NativeCoroutinesState
    val firstNameValidation: StateFlow<ValidationResult> = _firstName.map { Validator.validateCommon(it) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ValidationResult.Valid)

    @NativeCoroutinesState
    val lastNameValidation: StateFlow<ValidationResult> = _lastName.map { Validator.validateCommon(it) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ValidationResult.Valid)

    @NativeCoroutinesState
    val emailValidation: StateFlow<ValidationResult> = _email.map { Validator.validateEmail(it) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ValidationResult.Valid)

    @NativeCoroutinesState
    val passwordValidation: StateFlow<ValidationResult> = _password.map { Validator.validatePassword(it) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ValidationResult.Valid)

    @NativeCoroutinesState
    val confirmPasswordValidation: StateFlow<ValidationResult> = _confirmPassword.map {
        if (it == _password.value) Validator.validatePassword(it) else ValidationResult.Invalid("Passwords do not match")
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ValidationResult.Valid)

    fun setFirstName(firstName: String) {
        _firstName.value = firstName
    }

    fun setLastName(lastName: String) {
        _lastName.value = lastName
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    fun setFirstNameBlur(blurred: Boolean) {
        _firstNameBlurred.value = blurred
    }

    fun setLastNameBlur(blurred: Boolean) {
        _lastNameBlurred.value = blurred
    }

    fun setEmailBlur(blurred: Boolean) {
        _emailBlurred.value = blurred
    }

    fun setPasswordBlur(blurred: Boolean) {
        _passwordBlurred.value = blurred
    }

    fun setConfirmPasswordBlur(blurred: Boolean) {
        _confirmPasswordBlurred.value = blurred
    }

    fun setPasswordVisible(visible: Boolean) {
        _passwordVisible.value = visible
    }

    fun setConfirmPasswordVisible(visible: Boolean) {
        _confirmPasswordVisible.value = visible
    }
}