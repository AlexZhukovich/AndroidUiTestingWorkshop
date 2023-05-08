package com.alexzh.moodtracker.presentation.core

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SelectableEmotionItem(
    val emotionId: Long,
    @DrawableRes val iconRes: Int,
    @StringRes val contentDescription: Int,
    val isSelected: Boolean
)
