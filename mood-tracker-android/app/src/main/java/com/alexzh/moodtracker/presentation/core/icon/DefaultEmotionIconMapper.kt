package com.alexzh.moodtracker.presentation.core.icon

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.data.model.Emotion
import com.alexzh.moodtracker.presentation.core.EmotionItem
import com.alexzh.moodtracker.presentation.core.SelectableEmotionItem

class DefaultEmotionIconMapper : EmotionIconMapper {

    override fun mapToEmotionItem(
        emotion: Emotion,
        @StringRes contentDescription: Int,
        @DrawableRes fallbackIcon: Int
    ) = EmotionItem(
        emotionId = emotion.id,
        iconRes = mapIcon(emotion.icon, fallbackIcon),
        contentDescription = contentDescription
    )

    override fun mapToSelectableEmotionItem(
        emotion: Emotion,
        @StringRes contentDescription: Int,
        @DrawableRes fallbackIcon: Int,
        isSelected: Boolean
    ) = SelectableEmotionItem(
        emotionId = emotion.id,
        contentDescription = contentDescription,
        iconRes = mapIcon(emotion.icon, fallbackIcon),
        isSelected = isSelected
    )

    @DrawableRes
    private fun mapIcon(
        iconName: String,
        @DrawableRes fallbackIcon: Int
    ) = when (iconName) {
        "emotion-excited" -> R.drawable.ic_emotion_excited
        "emotion-happy" -> R.drawable.ic_emotion_happy
        "emotion-neutral" -> R.drawable.ic_emotion_neutral
        "emotion-confused" -> R.drawable.ic_emotion_confused
        "emotion-angry" -> R.drawable.ic_emotion_angry
        else -> fallbackIcon
    }
}