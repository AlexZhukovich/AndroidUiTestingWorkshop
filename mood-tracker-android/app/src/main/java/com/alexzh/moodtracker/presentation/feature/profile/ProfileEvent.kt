package com.alexzh.moodtracker.presentation.feature.profile

sealed class ProfileEvent {
    object GetUserInfo: ProfileEvent()
    object LogOut: ProfileEvent()
}
