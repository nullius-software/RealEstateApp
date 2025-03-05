package com.realestate.app.ui.screen

import android.widget.Toast
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

import com.realestate.app.data.remote.ApiClient
import com.realestate.app.data.repository.AuthRepositoryImpl
import com.realestate.app.ui.component.CustomInput
import com.realestate.app.viewModel.RegisterViewModel
import com.realestate.app.viewModel.UserViewModel
import com.realestate.app.viewModel.ValidationResult

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    userViewModel: UserViewModel,
    onRegister: () -> Unit,
    onClickUserAlreadyHasAccount: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val authRepository = AuthRepositoryImpl(ApiClient())
    val context = LocalContext.current

    val firstNameState = viewModel.firstName.collectAsState()
    val lastNameState = viewModel.lastName.collectAsState()
    val emailState = viewModel.email.collectAsState()
    val passwordState = viewModel.password.collectAsState()
    val confirmPasswordState = viewModel.confirmPassword.collectAsState()
    val passwordVisibleState = viewModel.passwordVisible.collectAsState()
    val confirmPasswordVisibleState = viewModel.confirmPasswordVisible.collectAsState()

    val firstNameValidation = viewModel.firstNameValidation.collectAsState()
    val lastNameValidation = viewModel.lastNameValidation.collectAsState()
    val emailValidation = viewModel.emailValidation.collectAsState()
    val passwordValidation = viewModel.passwordValidation.collectAsState()
    val confirmPasswordValidation = viewModel.confirmPasswordValidation.collectAsState()

    val firstNameBlurred = viewModel.firstNameBlurred.collectAsState()
    val lastNameBlurred = viewModel.lastNameBlurred.collectAsState()
    val emailBlurred = viewModel.emailBlurred.collectAsState()
    val passwordBlurred = viewModel.passwordBlurred.collectAsState()
    val confirmPasswordBlurred = viewModel.confirmPasswordBlurred.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Register",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            "Please enter your details to create an account.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        CustomInput(
            valueState = firstNameState,
            onValueChange = {
                viewModel.setFirstName(it)
            },
            label = "First Name",
            isBlurredState = firstNameBlurred,
            validation = firstNameValidation.value,
            onFocusChange = { focusState ->
                viewModel.setFirstNameBlur(false)
                if (!focusState.isFocused) {
                    viewModel.setFirstNameBlur(true)
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        CustomInput(
            valueState = lastNameState,
            onValueChange = {
                viewModel.setLastName(it)
            },
            label = "Last Name",
            isBlurredState = lastNameBlurred,
            validation = lastNameValidation.value,
            onFocusChange = { focusState ->
                viewModel.setLastNameBlur(false)
                if (!focusState.isFocused) {
                    viewModel.setLastNameBlur(true)
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

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
        Spacer(modifier = Modifier.height(8.dp))

        CustomInput(
            valueState = confirmPasswordState,
            onValueChange = {
                viewModel.setConfirmPassword(it)
            },
            label = "Confirm Password",
            isPassword = true,
            isVisibleState = confirmPasswordVisibleState,
            isBlurredState = confirmPasswordBlurred,
            validation = confirmPasswordValidation.value,
            onFocusChange = { focusState ->
                viewModel.setConfirmPasswordBlur(false)
                if (!focusState.isFocused) {
                    viewModel.setConfirmPasswordBlur(true)
                }
            },
            toggleIsVisible = {
                viewModel.setConfirmPasswordVisible(!confirmPasswordVisibleState.value)
            }
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            coroutineScope.launch {
                try {
                    val response = authRepository.register(
                        firstNameState.value,
                        lastNameState.value,
                        emailState.value,
                        passwordState.value
                    )

                    if (response.message.isNotEmpty()) {
                        userViewModel.setUserData(response.data)

                        Toast.makeText(context, "User successfully created", Toast.LENGTH_LONG).show()
                        onRegister()
                    } else {
                        throw Exception(response.error)
                    }
                }  catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        },
            enabled = (firstNameValidation.value == ValidationResult.Valid
                    && lastNameValidation.value == ValidationResult.Valid
                    && emailValidation.value == ValidationResult.Valid
                    && passwordValidation.value == ValidationResult.Valid
                    && confirmPasswordValidation.value == ValidationResult.Valid)
        ) {
            Text("Register")
        }
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