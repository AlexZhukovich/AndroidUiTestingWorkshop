package com.alexzh.moodtracker.presentation.core.icon

import androidx.annotation.DrawableRes
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.data.model.Activity
import com.alexzh.moodtracker.presentation.core.ActivityItem
import com.alexzh.moodtracker.presentation.core.SelectableActivityItem

class DefaultActivityIconMapper : ActivityIconMapper {

    override fun mapToActivityItem(
        activity: Activity,
        fallbackIcon: Int,
    ) = ActivityItem(
        id = activity.id,
        name = activity.name,
        icon = mapIcon(activity.icon, fallbackIcon)
    )

    override fun mapToSelectableActivityItem(
        activity: Activity,
        fallbackIcon: Int,
        isSelected: Boolean
    ) = SelectableActivityItem(
        id = activity.id,
        name = activity.name,
        icon = mapIcon(activity.icon, fallbackIcon),
        isSelected = isSelected
    )

    @DrawableRes
    private fun mapIcon(
        iconName: String,
        @DrawableRes fallbackIcon: Int
    ) = when (iconName) {
        "activity-sport" -> R.drawable.ic_activity_sport
        "activity-work" -> R.drawable.ic_activity_work
        "activity-gardening" -> R.drawable.ic_activity_gardening
        "activity-relax" -> R.drawable.ic_activity_relax
        "activity-reading" -> R.drawable.ic_activity_reading
        "activity-gaming" -> R.drawable.ic_activity_gaming
        "activity-shopping" -> R.drawable.ic_activity_shopping
        "activity-traveling" -> R.drawable.ic_activity_traveling
        "activity-friends" -> R.drawable.ic_activity_friends
        "activity-family" -> R.drawable.ic_activity_family
        "activity-walking" -> R.drawable.ic_activity_walking
        "activity-cleaning" -> R.drawable.ic_activity_cleaning
        "activity-cooking" -> R.drawable.ic_activity_cooking
        else -> fallbackIcon
    }
}