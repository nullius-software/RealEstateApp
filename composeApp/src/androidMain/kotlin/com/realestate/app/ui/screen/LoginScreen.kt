package com.realestate.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

import com.realestate.app.data.remote.ApiClient
import com.realestate.app.data.repository.AuthRepositoryImpl
import com.realestate.app.ui.component.CustomInput
import com.realestate.app.viewModel.LoginViewModel
import com.realestate.app.viewModel.ValidationResult

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLogin: () -> Unit,
    onClickUserHasNoAccount: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val authRepository = AuthRepositoryImpl(ApiClient())
    val emailState = viewModel.email.collectAsState()
    val passwordState = viewModel.password.collectAsState()
    val passwordVisibleState = viewModel.passwordVisible.collectAsState()
    val emailValidation = viewModel.emailValidation.collectAsState()
    val passwordValidation = viewModel.passwordValidation.collectAsState()
    val emailBlurred = viewModel.emailBlurred.collectAsState()
    val passwordBlurred = viewModel.passwordBlurred.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Login",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            "Please enter your email and password to continue.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomInput(
            valueState = emailState,
            onValueChange = {
                viewModel.setEmail(it)
            },
            label = "Email",
            isBlurredState = emailBlurred,
            validation = emailValidation.value,
            onFocusChange = { focusState ->
                viewModel.setEmailBlur(false)
                if (!focusState.isFocused) {
                    viewModel.setEmailBlur(true)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomInput(
            valueState = passwordState,
            onValueChange = {
                viewModel.setPassword(it)
            },
            label = "Password",
            isPassword = true,
            isVisibleState = passwordVisibleState,
            isBlurredState = passwordBlurred,
            validation = passwordValidation.value,
            onFocusChange = { focusState ->
                viewModel.setPasswordBlur(false)
                if (!focusState.isFocused) {
                    viewModel.setPasswordBlur(true)
                }
            },
            toggleIsVisible = {
                viewModel.setPasswordVisible(!passwordVisibleState.value)
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            coroutineScope.launch {
                val response = authRepository.login(emailState.value, passwordState.value)
                if (response.token != null) {
                    println("Login successful: ${response.token}")
                    onLogin()
                } else {
                    println("Login error: ${response.error}")
                }
            }
        },
            enabled = emailValidation.value == ValidationResult.Valid && passwordValidation.value == ValidationResult.Valid
        ) {
            Text("Login")
        }

        TextButton(onClick = {
            onClickUserHasNoAccount()
        }) {
            Text(
                "Don't have an account? Click here to register.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

