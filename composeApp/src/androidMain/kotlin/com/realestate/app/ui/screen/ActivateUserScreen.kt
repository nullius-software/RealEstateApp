package com.realestate.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import com.realestate.app.data.remote.ApiClient
import com.realestate.app.data.repository.AuthRepositoryImpl
import com.realestate.app.viewModel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ActivateUserScreen(userViewModel: UserViewModel, userVerified: () -> Unit) {
    val authRepository = AuthRepositoryImpl(ApiClient())
    val user = userViewModel.userData.value
    var emailResent by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    if (user == null) {
        return Text(
            "Unexpected error. User not found.",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }

    LaunchedEffect(Unit) {
        while (!userViewModel.userEmailIsVerified.value) {
            val verified = userViewModel.checkIfUserIsVerified()
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
        TextButton(
            onClick = {
                coroutineScope.launch {
                    emailResent = true
                    authRepository.resendVerificationEmail(user.externalId)
                }
            }, enabled = !emailResent
        ) {
            Text(if (emailResent) "Verification email sent" else "Resend verification email")
        }
    }
}