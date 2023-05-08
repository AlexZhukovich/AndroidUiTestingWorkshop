package com.alexzh.moodtracker.presentation.core.icon

import androidx.annotation.StringRes
import com.alexzh.moodtracker.R

class DefaultEmotionContentDescriptionMapper: EmotionContentDescriptionMapper {

    @StringRes
    override fun mapHappinessToContentDescription(
        happinessLevel: Int,
        @StringRes fallbackContentDescription: Int
    ): Int {
        return when (happinessLevel) {
            1 -> R.string.emotions_angry_contentDescription
            2 -> R.string.emotions_confused_contentDescription
            3 -> R.string.emotions_neutral_contentDescription
            4 -> R.string.emotions_happy_contentDescription
            5 -> R.string.emotions_excited_contentDescription
            else -> fallbackContentDescription
        }
    }
}