package com.alexzh.moodtracker.presentation.core.icon

import androidx.annotation.DrawableRes
import com.alexzh.moodtracker.data.model.Activity
import com.alexzh.moodtracker.presentation.core.ActivityItem
import com.alexzh.moodtracker.presentation.core.SelectableActivityItem

interface ActivityIconMapper {

    fun mapToActivityItem(
        activity: Activity,
        @DrawableRes fallbackIcon: Int
    ): ActivityItem

    fun mapToSelectableActivityItem(
        activity: Activity,
        @DrawableRes fallbackIcon: Int,
        isSelected: Boolean
    ): SelectableActivityItem
}