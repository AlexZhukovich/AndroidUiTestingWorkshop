package com.alexzh.moodtracker.presentation.core

import androidx.annotation.DrawableRes

data class SelectableActivityItem(
    val id: Long,
    val name: String,
    @DrawableRes val icon: Int,
    val isSelected: Boolean
)
