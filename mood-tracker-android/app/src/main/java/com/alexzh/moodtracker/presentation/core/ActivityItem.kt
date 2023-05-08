package com.alexzh.moodtracker.presentation.core

import androidx.annotation.DrawableRes

data class ActivityItem(
    val id: Long,
    val name: String,
    @DrawableRes val icon: Int,
)
