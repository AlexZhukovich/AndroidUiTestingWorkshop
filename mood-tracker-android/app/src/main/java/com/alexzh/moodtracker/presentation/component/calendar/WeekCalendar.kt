package com.alexzh.moodtracker.presentation.component.calendar

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.theme.AppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.getNextSevenDates(): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    repeat(7) { day ->
        dates.add(this.plusDays(day.toLong()))
    }
    return dates
}

@ExperimentalMaterial3Api
@Composable
fun WeekCalendar(
    modifier: Modifier = Modifier,
    startDate: LocalDate,
    selectedDate: LocalDate = startDate,
    onSelectedDateChange: (LocalDate) -> Unit,
    todayDate: LocalDate = LocalDate.now(),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    todayIndicatorBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    todayIndicatorTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    selectedDateIndicatorBackgroundColor: Color = MaterialTheme.colorScheme.secondary,
    selectedDateIndicatorTextColor: Color = MaterialTheme.colorScheme.onSecondary,
    unselectedDateIndicatorTextColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
        ) {
            val weekData = remember { mutableStateOf(startDate.getNextSevenDates()) }

            WeekCalendarHeader(
                lastDate = weekData.value.last(),
                onPreviousWeek = { weekData.value = weekData.value.first().minusDays(7L).getNextSevenDates() },
                onToday = {
                    weekData.value = LocalDate.now().minusDays(6L).getNextSevenDates()
                    onSelectedDateChange(LocalDate.now())
                },
                onNextWeek = { weekData.value = weekData.value.last().plusDays(1L).getNextSevenDates() }
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(weekData.value) { day ->
                    Column(
                        modifier.fillMaxWidth().height(68.dp)
                            .padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = day.format(DateTimeFormatter.ofPattern("EEE"))
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            modifier = Modifier.fillMaxSize()
                                .clip(MaterialTheme.shapes.medium)
                                .background(
                                    if (day == todayDate) {
                                        todayIndicatorBackgroundColor
                                    } else {
                                        if (day == selectedDate) {
                                            selectedDateIndicatorBackgroundColor
                                        } else {
                                            Color.Transparent
                                        }
                                    }
                                )
                                .clickable {
                                    onSelectedDateChange(day)
                                }
                                .wrapContentHeight(),
                            text = day.dayOfMonth.toString(),
                            color = if (day == LocalDate.now()) {
                                    todayIndicatorTextColor
                                } else {
                                    if (day == selectedDate) {
                                        selectedDateIndicatorTextColor
                                    } else {
                                        unselectedDateIndicatorTextColor
                                    }
                                },
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeekCalendarHeader(
    lastDate: LocalDate,
    onNextWeek: () -> Unit,
    onPreviousWeek: () -> Unit,
    onToday: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f).padding(start = 8.dp),
            text = lastDate.format(DateTimeFormatter.ofPattern("MMMM uuuu")),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        IconButton(
            modifier = Modifier.size(32.dp),
            onClick = onPreviousWeek
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_small_arrow_left),
                contentDescription = stringResource(R.string.statisticsScreen_previousWeek_label),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(
            modifier = Modifier.size(32.dp),
            onClick = onToday
        ) {
            Icon(
                modifier = Modifier.size(24.dp).padding(),
                painter = painterResource(R.drawable.ic_nav_today),
                contentDescription = stringResource(R.string.statisticsScreen_currentWeek_label),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(
            modifier = Modifier.size(32.dp),
            onClick = onNextWeek
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_small_arrow_right),
                contentDescription = stringResource(R.string.statisticsScreen_nextWeek_label),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun Demo_WeekCalendar() {
    val selectedData = remember { mutableStateOf(LocalDate.now()) }

    AppTheme {
        Column {
            WeekCalendar(
                modifier = Modifier.fillMaxWidth(),
                startDate = LocalDate.now().minusDays(6L),
                selectedDate = selectedData.value,
                onSelectedDateChange = { selectedData.value = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = selectedData.value.toString()
            )
        }
    }
}