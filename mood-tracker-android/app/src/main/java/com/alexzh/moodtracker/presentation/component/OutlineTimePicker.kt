package com.alexzh.moodtracker.presentation.component

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlineTimePicker(
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    value: LocalTime,
    is24HourView: Boolean = true,
    formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(if (is24HourView) "HH:mm" else "hh:mm a"),
    icon: ImageVector = Icons.Filled.DateRange,
    iconContentDescription: String = "Select a time",
    onValueChange: (LocalTime) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val datePickerDialog = remember {
        TimePickerDialog(
            context, {_: TimePicker, hour: Int, minute: Int ->
                onValueChange(LocalTime.of(hour, minute))
                focusManager.clearFocus()
            }, value.hour, value.minute, is24HourView
        ).apply {
            setOnDismissListener {
                focusManager.clearFocus()
            }
        }
    }

    OutlinedTextField(
        modifier = modifier.onFocusChanged {
            if (it.isFocused)
                datePickerDialog.show()
            else
                datePickerDialog.dismiss()
        },
        label = label,
        value = value.format(formatter),
        onValueChange = { onValueChange( LocalTime.parse(it, formatter)) },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = iconContentDescription,
                modifier = Modifier.clickable { datePickerDialog.show() }
            )
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun Preview_OutlineTimePicker() {
    val date1 = remember { mutableStateOf(LocalTime.now()) }
    val date2 = remember { mutableStateOf(LocalTime.now()) }
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            OutlineTimePicker(
                label = { Text("Time") },
                value = date1.value,
                onValueChange = { date1.value = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlineTimePicker(
                label = { Text("Time") },
                value = date2.value,
                formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG),
                onValueChange = { date2.value = it }
            )
        }
    }
}