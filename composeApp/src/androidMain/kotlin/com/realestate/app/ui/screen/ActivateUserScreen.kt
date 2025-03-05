package com.realestate.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import com.realestate.app.data.model.UserData
import com.realestate.app.data.remote.ApiClient
import com.realestate.app.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.delay

@Composable
fun ActivateUserScreen(user: UserData?, userVerified: () -> Unit) {
    val authRepository = AuthRepositoryImpl(ApiClient())
    val isVerified by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (!isVerified && user != null) {
            val verified = authRepository.checkIfUserIsVerified(user.externalId)
            if (verified) {
                userVerified()
            }
            delay(5000)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Verification email sent. Please check your inbox.",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextButton(onClick = { /* TODO: Implement resend email functionality */ }) {
            Text("Resend verification email")
        }
    }
}