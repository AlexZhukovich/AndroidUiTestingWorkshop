package com.alexzh.moodtracker.presentation.core.icon

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alexzh.moodtracker.data.model.Emotion
import com.alexzh.moodtracker.presentation.core.EmotionItem
import com.alexzh.moodtracker.presentation.core.SelectableEmotionItem

interface EmotionIconMapper {

    fun mapToEmotionItem(
        emotion: Emotion,
        @StringRes contentDescription: Int,
        @DrawableRes fallbackIcon: Int
    ): EmotionItem

    fun mapToSelectableEmotionItem(
        emotion: Emotion,
        contentDescription: Int,
        @DrawableRes fallbackIcon: Int,
        isSelected: Boolean
    ): SelectableEmotionItem
}