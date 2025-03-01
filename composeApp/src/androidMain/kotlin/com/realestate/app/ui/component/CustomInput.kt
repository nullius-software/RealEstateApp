package com.realestate.app.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.realestate.app.viewModel.ValidationResult

@Composable
fun CustomInput(
    valueState: State<String>,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isVisibleState: State<Boolean>? = null,
    toggleIsVisible: () -> Unit? = { },
    isBlurredState: State<Boolean>,
    validation: ValidationResult,
    onFocusChange: (FocusState) -> Unit
) {
    val showError = isBlurredState.value && valueState.value.isNotEmpty() && validation is ValidationResult.Invalid

    Column(modifier) {
        TextField(
            value = valueState.value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier.onFocusChanged { onFocusChange(it) },
            label = { Text(label) },
            singleLine = true,
            isError = showError,
            visualTransformation = if (isPassword && isVisibleState != null && !isVisibleState.value) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = if (isPassword && isVisibleState != null) {
                {
                    val image = if (isVisibleState.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { toggleIsVisible() }) {
                        Icon(imageVector = image, contentDescription = if (isVisibleState.value) "Hide password" else "Show password")
                    }
                }
            } else null
        )
        if (showError) {
            Text(
                (validation as ValidationResult.Invalid).error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}