package com.alexzh.moodtracker.presentation.component.text

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.alexzh.moodtracker.R

@Composable
fun NameOutlinedTextFieldWithError(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.genericTextField_name_label),
    placeholder: String = stringResource(R.string.genericTextField_name_placeholder),
    enabled: Boolean = true,
    isError: Boolean = false,
    errorLabel: String? = null
) {
    OutlinedTextFieldWithError(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        enabled = enabled,
        isError = isError,
        errorLabel = errorLabel,
    )
}

@Composable
fun EmailOutlinedTextFieldWithError(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.genericTextField_email_label),
    placeholder: String = stringResource(R.string.genericTextField_email_placeholder),
    enabled: Boolean = true,
    isError: Boolean = false,
    errorLabel: String? = null
) {
    OutlinedTextFieldWithError(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        enabled = enabled,
        isError = isError,
        errorLabel = errorLabel,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_email),
                contentDescription = stringResource(R.string.genericTextField_email_contentDescription),
                tint = MaterialTheme.colorScheme.outline
            )
        }
    )
}

@Composable
fun PasswordOutlinedTextFieldWithError(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.genericTextField_password_label),
    placeholder: String = stringResource(R.string.genericTextField_password_placeholder),
    enabled: Boolean = true,
    isError: Boolean = false,
    errorLabel: String? = null
) {
    val passwordVisible = remember { mutableStateOf(false) }
    val visualTransformation = if (passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()

    OutlinedTextFieldWithError(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        enabled = enabled,
        visualTransformation = visualTransformation,
        isError = isError,
        errorLabel = errorLabel,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_password),
                contentDescription = stringResource(R.string.genericTextField_password_contentDescription),
                tint = MaterialTheme.colorScheme.outline
            )
        },
        trailingIcon = {
            val visibilityIcon = painterResource(
                if (passwordVisible.value)
                    R.drawable.ic_visibility_on
                else
                    R.drawable.ic_visibility_off
            )

            val contentDescription = if (passwordVisible.value) {
                stringResource(R.string.passwordField_hidePassword_label)
            } else {
                stringResource(R.string.passwordField_showPassword_label)
            }
            IconButton(onClick = {
                passwordVisible.value = !passwordVisible.value
            }) {
                Icon(
                    painter = visibilityIcon,
                    contentDescription = contentDescription
                )
            }
        }
    )
}
