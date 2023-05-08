package com.alexzh.moodtracker.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun LoadingButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    loadingIndicatorColor: Color = MaterialTheme.colorScheme.surface
) {
    Button(
        modifier = modifier,
        onClick = {
            if (!isLoading) {
                onClick()
            }
        },
        enabled = enabled,
        shape = shape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = loadingIndicatorColor,
                    strokeWidth = 2.dp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text)
        }
    }
}