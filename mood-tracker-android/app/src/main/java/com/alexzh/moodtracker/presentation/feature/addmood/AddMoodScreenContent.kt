package com.alexzh.moodtracker.presentation.feature.addmood

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.component.OutlineDatePicker
import com.alexzh.moodtracker.presentation.component.OutlineTimePicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalMaterial3Api
@Composable
fun AddMoodScreenContent(
    emotionHistoryId: Long,
    viewModel: AddMoodViewModel,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier,
                snackbar = { Snackbar(it) }
            )
        },
        content = { paddingValues ->
            val uiState = viewModel.state.value

            when {
                uiState.loading != AddMoodScreenLoading.NONE -> {
                    when (uiState.loading) {
                        AddMoodScreenLoading.LOADING,
                        AddMoodScreenLoading.SAVING,
                        AddMoodScreenLoading.DELETING -> LoadingScreen(paddingValues)
                        else -> { /* Already covered in previous when operator */ }
                    }
                }
                uiState.completedOperation != AddMoodScreenCompletedOperation.NONE -> {
                    when (uiState.completedOperation) {
                        AddMoodScreenCompletedOperation.SAVED,
                        AddMoodScreenCompletedOperation.DELETED -> onBack()
                        else -> { /* Already covered in previous when operator */ }
                    }
                }
                else -> {
                    LoadedSuccessfullyScreen(
                        paddingValues,
                        uiState,
                        snackbarHostState,
                        viewModel
                    )
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.onEvent(AddMoodEvent.Load(emotionHistoryId))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@ExperimentalMaterial3Api
@Composable
private fun LoadedSuccessfullyScreen(
    paddingValues: PaddingValues,
    state: AddMoodScreenState,
    snackbarState: SnackbarHostState,
    viewModel: AddMoodViewModel
) {
    val scope = rememberCoroutineScope()

    val selectedEmotion = state.emotions.firstOrNull { it.isSelected }
    val errorMessage = stringResource(R.string.addMoodScreen_error_selectEmotionAndActivity)
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                start = 0.dp,
                top = paddingValues.calculateTopPadding(),
                end = 0.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth().height(72.dp).padding(bottom = 8.dp),
            columns = GridCells.Fixed(5)
        ) {
            items(state.emotions) { emotion ->
                Icon(
                    modifier = Modifier.size(60.dp).padding(4.dp)
                        .clip(shape = CircleShape)
                        .clickable {
                            viewModel.onEvent(AddMoodEvent.OnEmotionChange(emotion.emotionId))
                        },
                    painter = painterResource(emotion.iconRes),
                    contentDescription = stringResource(emotion.contentDescription),
                    tint = if (emotion.emotionId == selectedEmotion?.emotionId) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlineDatePicker(
                modifier = Modifier.fillMaxWidth().weight(1.0f),
                label = { Text(stringResource(R.string.addMoodScreen_date_label)) },
                value = state.date,
                onValueChange = { viewModel.onEvent(AddMoodEvent.OnDateChange(it)) }
            )
            OutlineTimePicker(
                modifier = Modifier.fillMaxWidth().weight(1.0f),
                label = { Text(stringResource(R.string.addMoodScreen_time_label)) },
                value = state.time,
                is24HourView = state.is24hFormat,
                onValueChange = { viewModel.onEvent(AddMoodEvent.OnTimeChange(it)) }
            )
        }

        AnimatedVisibility(
            visible = selectedEmotion != null,
            enter = fadeIn()
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.activities.forEach { activity ->
                    FilterChip(
                        selected = activity.isSelected,
                        label = {
                            Text(
                                text = activity.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                                style = MaterialTheme.typography.bodySmall,
                            )
                        },
                        onClick = {
                            viewModel.onEvent(AddMoodEvent.OnActivityChange(activity.id))
                        },
                        enabled = true,
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(activity.icon),
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            if (activity.isSelected) {
                                Icon(
                                    modifier = Modifier.size(18.dp),
                                    painter = painterResource(R.drawable.ic_check),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = selectedEmotion != null,
            enter = fadeIn()
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(8.dp)
                    .onFocusEvent {
                        if (it.isFocused) {

                            scope.launch {
                                delay(200)
                                listState.animateScrollToItem(3)

                            }
                        }
                    },
                value = state.note,
                onValueChange = { viewModel.onEvent(AddMoodEvent.OnNoteChange(it)) },
                label = { Text(stringResource(R.string.addMoodScreen_note_label)) }
            )
        }

        AnimatedVisibility(
            visible = selectedEmotion != null,
            enter = fadeIn()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                onClick = {
                    val selectedActions = state.activities.filter { it.isSelected }
                    if (selectedEmotion != null && selectedActions.isNotEmpty()) {
                        viewModel.onEvent(AddMoodEvent.Save)
                    } else {
                        scope.launch {
                            snackbarState.showSnackbar(errorMessage)
                        }
                    }
                }
            ) {
                Text(stringResource(R.string.addMoodScreen_save_title))
            }
        }
    }
}

@Composable
private fun LoadingScreen(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}