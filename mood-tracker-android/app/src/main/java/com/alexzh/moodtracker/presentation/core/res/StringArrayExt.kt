package com.alexzh.moodtracker.presentation.core.res

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun Array<String>.highlightLastItem(): AnnotatedString {
    return buildAnnotatedString {
        this@highlightLastItem.forEachIndexed { index, value ->
            if (index == this@highlightLastItem.lastIndex) {
                append(
                    AnnotatedString(
                        text = value,
                        spanStyle = SpanStyle(MaterialTheme.colorScheme.primary)
                    )
                )
            } else {
                append(value)
            }
        }
    }
}