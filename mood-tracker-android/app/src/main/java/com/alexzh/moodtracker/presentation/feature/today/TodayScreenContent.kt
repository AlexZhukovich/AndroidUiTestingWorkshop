package com.alexzh.moodtracker.presentation.feature.today

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.component.Section
import com.alexzh.moodtracker.presentation.component.calendar.WeekCalendar
import com.alexzh.moodtracker.presentation.feature.today.model.EmotionHistoryItem
import java.util.*

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun TodayScreenContent(
    viewModel: TodayViewModel,
    onAdd: () -> Unit,
    onEdit: (Long) -> Unit
) {
    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                WeekCalendar(
                    startDate = uiState.date.minusDays(6),
                    selectedDate = uiState.date,
                    onSelectedDateChange = { viewModel.onEvent(TodayEvent.OnDateChange(it)) }
                )
            }
        },
        floatingActionButton = {
             ExtendedFloatingActionButton(
                 onClick = { onAdd() },
                 text = { Text(stringResource(R.string.todayScreen_add_label)) },
                 icon = {
                     Icon(
                         Icons.Filled.Add,
                         contentDescription = stringResource(R.string.todayScreen_add_label)
                     )
                 }
             )
        },
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding() + 4.dp,
                        bottom = it.calculateBottomPadding() + 8.dp
                    )
            ) {
                item {
                    Section(stringResource(R.string.todayScreen_emotions_label)) {
                        when {
                            uiState.isLoading -> LoadingScreen()
                            uiState.items.isEmpty() -> EmptyScreen()
                            else -> SuccessScreen(uiState.items, onEdit)
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxWidth().height(72.dp * 2),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyScreen() {
    Box(
        modifier = Modifier.fillMaxWidth().height(72.dp * 2),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(72.dp),
                painter = painterResource(R.drawable.ic_no_data),
                contentDescription = null
            )
            Text(stringResource(R.string.todayScreen_noData_label), fontSize = 18.sp)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun SuccessScreen(
    items: List<EmotionHistoryItem>,
    onEdit: (Long) -> Unit,
) {
    val lastIndex = items.lastIndex
    Column(modifier = Modifier.fillMaxWidth()) {
        items.forEachIndexed { index, item ->
            EmotionHistoryItem(item, onEdit)
            if (index < lastIndex) {
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@ExperimentalMaterial3Api
@Composable
private fun EmotionHistoryItem(
    item: EmotionHistoryItem,
    onEdit: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onEdit(item.id) }
            .padding(4.dp)
    ) {
        Icon(
            modifier = Modifier.size(64.dp),
            painter = painterResource(item.iconId),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            contentDescription = stringResource(item.iconContentDescription)
        )

        Column(
            modifier = Modifier.weight(1.0f)
                .padding(horizontal = 8.dp)
        ) {
            item.note?.let {
                Text(text = it, fontSize = 14.sp)
            }

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item.activities.forEach { activity ->
                    AssistChip(
                        onClick = {},
                        label = {
                            Text(
                                text = activity.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                                style = MaterialTheme.typography.bodySmall,
                            )
                        },
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(activity.icon),
                                contentDescription = null
                            )
                        },
                        enabled = false,
                        border = AssistChipDefaults.assistChipBorder(disabledBorderColor = MaterialTheme.colorScheme.outline),
                        colors = AssistChipDefaults.assistChipColors(
                            disabledLeadingIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }

            Text(
                text = item.formattedDate, style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            )
        }
    }
}