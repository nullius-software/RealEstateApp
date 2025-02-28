package com.realestate.app.screens

import android.provider.CalendarContract.Colors
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
fun LoginScreen(
    onLogin: () -> Unit,
    onClickUserHasNoAccount: () -> Unit,
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
            "Login",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            "Please enter your email and password to continue.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
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
            singleLine = true
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            coroutineScope.launch {
                val response = ApiClient().login(emailState.value, passwordState.value)
                if (response.token != null) {
                    println("Login exitoso: ${response.token}")
                    onLogin()
                } else {
                    println("Error en login: ${response.error}")
                }
            }
        }) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onClickUserHasNoAccount()
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            )
        ) {
            Text(
                "Don't have an account? Click here to register.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

