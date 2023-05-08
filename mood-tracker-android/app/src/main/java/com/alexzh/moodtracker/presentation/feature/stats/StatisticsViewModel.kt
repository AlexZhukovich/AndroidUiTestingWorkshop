package com.alexzh.moodtracker.presentation.feature.stats

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.data.EmotionHistoryRepository
import com.alexzh.moodtracker.data.local.adapter.DATE_TIME_ZONE_UTC
import com.alexzh.moodtracker.presentation.feature.stats.model.DateToHappinessStatistics
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class StatisticsViewModel(
    private val emotionHistoryRepository: EmotionHistoryRepository
): ViewModel() {
    companion object {
        const val DEFAULT_STATISTICS_PERIOD = 6L
    }

    private var dateToFetchData = LocalDate.now()
    private val _state = mutableStateOf(StatisticsScreenState())
    val state: State<StatisticsScreenState> = _state

    fun onEvent(event: StatisticsEvent) {
        when (event) {
            is StatisticsEvent.LoadCurrentWeek -> load(LocalDate.now())
            is StatisticsEvent.LoadPreviousWeek -> load(dateToFetchData.minusDays(DEFAULT_STATISTICS_PERIOD))
            is StatisticsEvent.LoadNextWeek -> load(dateToFetchData.plusDays(DEFAULT_STATISTICS_PERIOD))
        }
    }

    private fun load(endDate: LocalDate) {
        dateToFetchData = endDate
        viewModelScope.launch {
            val formattedDatePeriod = getFormattedDatePeriod(
                startDate = endDate.minusDays(DEFAULT_STATISTICS_PERIOD),
                endDate = endDate
            )
            _state.value = _state.value.copy(
                loading = true,
                dateToHappinessData = DateToHappinessStatistics(
                    datePeriod = formattedDatePeriod,
                    items = emptyList()
                )
            )

            val result = emotionHistoryRepository.getDayToAverageHappinessLevel(
                startDate = ZonedDateTime.of(endDate.minusDays(DEFAULT_STATISTICS_PERIOD), LocalTime.of(0,0, 0), DATE_TIME_ZONE_UTC),
                endDate = ZonedDateTime.of(endDate, LocalTime.of(23,59, 59), DATE_TIME_ZONE_UTC)
            )
            if (result.isEmpty()) {
                _state.value = _state.value.copy(
                    loading = false,
                    dateToHappinessData = DateToHappinessStatistics(
                        datePeriod = formattedDatePeriod,
                        items = emptyList()
                    )
                )
            } else {
                val currentWeekData = result
                    .map { it.date.toLocalDate() to it.happinessLevel }
                    .groupBy { it.first }.mapValues { entity ->
                        (entity.value.sumOf { it.second } / entity.value.size).toFloat()
                    }
                    .filter { it.key >= endDate.minusDays(DEFAULT_STATISTICS_PERIOD) && it.key <= endDate }
                    .toMutableMap()

                (DEFAULT_STATISTICS_PERIOD downTo 0L).forEach {
                    if (!currentWeekData.containsKey(endDate.minusDays(it))) {
                        currentWeekData[endDate.minusDays(it)] = 0.toFloat()
                    }
                }

                _state.value = _state.value.copy(
                    loading = false,
                    dateToHappinessData = DateToHappinessStatistics(
                        datePeriod = formattedDatePeriod,
                        items = currentWeekData.toList().sortedBy { it.first }
                    )
                )
            }
        }
    }

    private fun getFormattedDatePeriod(
        startDate: LocalDate,
        endDate: LocalDate,
        formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM dd")
    ): String {
        return "${startDate.format(formatter)} - ${endDate.format(formatter)}"
    }
}