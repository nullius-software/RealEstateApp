package com.realestate.app.viewModel

import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class LoginViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _passwordVisible = MutableStateFlow(false)
    private val _emailBlurred = MutableStateFlow(false)
    private val _passwordBlurred = MutableStateFlow(false)

    @NativeCoroutinesState
    val email: StateFlow<String> = _email

    @NativeCoroutinesState
    val password: StateFlow<String> = _password

    @NativeCoroutinesState
    val passwordVisible: StateFlow<Boolean> = _passwordVisible

    @NativeCoroutinesState
    val emailBlurred: StateFlow<Boolean> = _emailBlurred

    @NativeCoroutinesState
    val passwordBlurred: StateFlow<Boolean> = _passwordBlurred

    @NativeCoroutinesState
    val emailValidation: StateFlow<ValidationResult> = _email.map { Validator.validateEmail(it) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ValidationResult.Valid)

    @NativeCoroutinesState
    val passwordValidation: StateFlow<ValidationResult> = _password.map { Validator.validatePassword(it) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ValidationResult.Valid)

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setPasswordVisible(passwordVisible: Boolean) {
        _passwordVisible.value = passwordVisible
    }

    fun setEmailBlur(blurred: Boolean) {
        _emailBlurred.value = blurred
    }

    fun setPasswordBlur(blurred: Boolean) {
        _passwordBlurred.value = blurred
    }
}