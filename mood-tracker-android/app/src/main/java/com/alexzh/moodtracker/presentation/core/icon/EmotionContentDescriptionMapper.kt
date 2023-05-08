package com.alexzh.moodtracker.presentation.core.icon

import androidx.annotation.StringRes

interface EmotionContentDescriptionMapper {

    @StringRes
    fun mapHappinessToContentDescription(
        happinessLevel: Int,
        @StringRes fallbackContentDescription: Int
    ): Int
}