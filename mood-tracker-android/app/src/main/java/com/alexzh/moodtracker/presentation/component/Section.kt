package com.alexzh.moodtracker.presentation.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun Section(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
            .animateContentSize()
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
            style = MaterialTheme.typography.titleMedium,
            text = title
        )
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            content()
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun Preview_SectionWithCustomContent() {
    Section(title = "Test section") {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("test content")
        }
    }
}