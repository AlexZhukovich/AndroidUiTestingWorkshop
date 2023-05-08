package com.alexzh.moodtracker.presentation.component.text

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedTextFieldWithError(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorLabel: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
) {
    val composableLabel: @Composable (() -> Unit)? = if (label != null) {
        { Text(label) }
    } else null

    val composablePlaceholder: @Composable (() -> Unit)? = if (placeholder != null) {
        { Text(placeholder) }
    } else null

    val composableError: @Composable (() -> Unit)? = if (errorLabel != null) {
        {
            Text(
                text = errorLabel,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
    } else null

    OutlinedTextFieldWithError(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        label = composableLabel,
        placeholder = composablePlaceholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        errorLabel = composableError,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        maxLines = maxLines
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldWithError(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorLabel: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation,
            singleLine = singleLine,
            maxLines = maxLines
        )
        errorLabel?.let {
            it()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun Preview_OutlinedTextFieldWithError_Multiline_NoError() {
    val text = remember { mutableStateOf("Long long long long long long long long text ") }

    OutlinedTextFieldWithError(
        modifier = Modifier.width(240.dp),
        value = text.value,
        onValueChange = { text.value = it },
        leadingIcon = { Icon(Icons.Outlined.Add, contentDescription = null)},
        trailingIcon = { Icon(Icons.Outlined.Build, contentDescription = null)},
        label = "Label"
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun Preview_OutlinedTextFieldWithError_Multiline_Error() {
    val text = remember { mutableStateOf("Long long long long long long long long text ") }

    OutlinedTextFieldWithError(
        modifier = Modifier.width(240.dp),
        value = text.value,
        onValueChange = { text.value = it },
        label = "Label",
        leadingIcon = { Icon(Icons.Outlined.Add, contentDescription = null)},
        trailingIcon = { Icon(Icons.Outlined.Build, contentDescription = null)},
        isError = true,
        errorLabel = "Error message"
    )
}