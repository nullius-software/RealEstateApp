package com.realestate.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.realestate.app.services.ApiClient
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegister: () -> Unit,
    onClickUserAlreadyHasAccount: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val coroutineScope = rememberCoroutineScope()

        Text(
            "Register",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            "Please enter your details to create an account.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        val firstNameState = remember { mutableStateOf("") }
        TextField(
            value = firstNameState.value,
            onValueChange = { firstNameState.value = it },
            label = { Text("First Name") },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        val lastNameState = remember { mutableStateOf("") }
        TextField(
            value = lastNameState.value,
            onValueChange = { lastNameState.value = it },
            label = { Text("Last Name") },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        val emailState = remember { mutableStateOf("") }
        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        val passwordState = remember { mutableStateOf("") }
        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        val confirmPasswordState = remember { mutableStateOf("") }
        TextField(
            value = confirmPasswordState.value,
            onValueChange = { confirmPasswordState.value = it },
            label = { Text("Confirm Password") },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            coroutineScope.launch {
                val email = emailState.value
                val password = passwordState.value
                val confirmPassword = confirmPasswordState.value

                if (password != confirmPassword) {
                    println("Passwords do not match")
                    return@launch
                }

                if (password.length < 8) {
                    println("Password must be at least 8 characters long")
                    return@launch
                }

                if (!password.any { it.isDigit() }) {
                    println("Password must contain at least one digit")
                    return@launch
                }

                if (!password.any { it.isUpperCase() }) {
                    println("Password must contain at least one uppercase letter")
                    return@launch
                }

                val response = ApiClient().register(
                    firstNameState.value,
                    lastNameState.value,
                    email,
                    password
                )
                if (response.success) {
                    println("Registration successful")
                    onRegister()
                } else {
                    println("Registration error: ${response.error}")
                }
            }
        }) {
            Text("Register")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onClickUserAlreadyHasAccount()
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            )
        ) {
            Text(
                "Already have an account? Click here to log in",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}