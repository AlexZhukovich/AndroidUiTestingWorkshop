package com.alexzh.moodtracker.presentation.feature.addmood

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.data.EmotionHistoryRepository
import com.alexzh.moodtracker.data.local.adapter.DATE_TIME_ZONE_UTC
import com.alexzh.moodtracker.data.local.preferences.AppPreferenceManager
import com.alexzh.moodtracker.data.model.EmotionHistory
import com.alexzh.moodtracker.data.util.Result
import com.alexzh.moodtracker.presentation.core.icon.ActivityIconMapper
import com.alexzh.moodtracker.presentation.core.icon.EmotionContentDescriptionMapper
import com.alexzh.moodtracker.presentation.core.icon.EmotionIconMapper
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

class AddMoodViewModel(
    private val emotionHistoryRepository: EmotionHistoryRepository,
    private val activityIconMapper: ActivityIconMapper,
    private val emotionIconMapper: EmotionIconMapper,
    private val emotionContentDescriptionMapper: EmotionContentDescriptionMapper,
    private val appPreferenceManager: AppPreferenceManager
) : ViewModel() {
    private var emotionHistory: EmotionHistory? = null

    private val _state = mutableStateOf(AddMoodScreenState())
    val state: State<AddMoodScreenState> = _state

    fun onEvent(event: AddMoodEvent) {
        when (event) {
            is AddMoodEvent.Load -> load(event.emotionHistoryId)
            is AddMoodEvent.OnEmotionChange -> onEmotionChange(event.emotionId)
            is AddMoodEvent.OnDateChange -> onDateChange(event.date)
            is AddMoodEvent.OnTimeChange -> onTimeChange(event.time)
            is AddMoodEvent.OnActivityChange -> onActivityChange(event.activityId)
            is AddMoodEvent.OnNoteChange -> onNodeChange(event.note)
            is AddMoodEvent.Delete -> delete()
            is AddMoodEvent.Save -> save()
        }
    }

    private fun load(emotionHistoryId: Long) {
        viewModelScope.launch {
            val is24hFormatActive = appPreferenceManager.is24hFormatActive().get()

            emotionHistoryRepository.getEmotionHistoryById(emotionHistoryId).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        emotionHistory = null
                        _state.value = _state.value.copy(
                            loading = AddMoodScreenLoading.LOADING,
                            emotions = emptyList(),
                            activities = emptyList(),
                            completedOperation = AddMoodScreenCompletedOperation.NONE,
                            is24hFormat = is24hFormatActive
                        )
                    }
                    is Result.Success -> {
                        emotionHistory = result.data
                    }
                    is Result.Error -> {
                        emotionHistory = null
                    }
                }
            }

            val emotions = emotionHistoryRepository.getEmotions()
            val activities = emotionHistoryRepository.getActivities()
            _state.value = _state.value.copy(
                loading = AddMoodScreenLoading.NONE,
                date = emotionHistory?.date?.toLocalDate() ?: LocalDate.now(),
                time = emotionHistory?.date?.toLocalTime() ?: LocalTime.now(),
                emotions = emotions.map {
                    emotionIconMapper.mapToSelectableEmotionItem(
                        emotion = it,
                        contentDescription = emotionContentDescriptionMapper.mapHappinessToContentDescription(
                            it.happinessLevel,
                            R.string.emotions_unknown_contentDescription
                        ),
                        fallbackIcon = R.drawable.ic_question_mark,
                        emotionHistory?.emotion?.id == it.id,
                    )
                },
                activities = activities.map { activity ->
                    activityIconMapper.mapToSelectableActivityItem(
                        activity,
                        R.drawable.ic_question_mark,
                        emotionHistory?.activities?.firstOrNull { it.id == activity.id } != null
                    )
                },
                note = emotionHistory?.note ?: "",
                is24hFormat = is24hFormatActive
            )
        }
    }

    private fun onEmotionChange(id: Long) {
        _state.value = _state.value.copy(emotions = _state.value.emotions.map {
            if (it.emotionId == id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                if (it.isSelected) {
                    it.copy(isSelected = false)
                } else {
                    it
                }
            }
        })
    }

    private fun onDateChange(date: LocalDate) {
        _state.value = _state.value.copy(date = date)
    }

    private fun onTimeChange(time: LocalTime) {
        _state.value = _state.value.copy(time = time)
    }

    private fun onActivityChange(id: Long) {
        _state.value = _state.value.copy(activities = _state.value.activities.map {
            if (it.id == id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        })
    }

    private fun onNodeChange(note: String) {
        _state.value = _state.value.copy(note = note)
    }

    private fun delete() {
        emotionHistory?.let {
            _state.value = _state.value.copy(loading = AddMoodScreenLoading.DELETING)
            viewModelScope.launch {
                emotionHistoryRepository.deleteEmotionHistory(it)

                _state.value = _state.value.copy(
                    loading = AddMoodScreenLoading.NONE,
                    completedOperation = AddMoodScreenCompletedOperation.DELETED
                )
            }
        }
    }

    private fun save() {
        _state.value = _state.value.copy(loading = AddMoodScreenLoading.SAVING)

        viewModelScope.launch {
            emotionHistoryRepository.insertOrUpdateEmotionHistory(
                id = emotionHistory?.id,
                date = ZonedDateTime.of(_state.value.date, _state.value.time, DATE_TIME_ZONE_UTC),
                emotionId = _state.value.emotions.first { it.isSelected }.emotionId,
                selectedActivityIds = _state.value.activities.filter { it.isSelected }.map { it.id },
                note = _state.value.note
            )
            _state.value = AddMoodScreenState(
                loading = AddMoodScreenLoading.NONE,
                completedOperation = AddMoodScreenCompletedOperation.SAVED
            )
        }
    }
}