package com.alexzh.moodtracker.presentation.feature.profile

import androidx.annotation.StringRes
import com.alexzh.moodtracker.data.remote.model.UserInfoModel

data class ProfileScreenState(
    val loading: Boolean = false,
    val userInfoModel: UserInfoModel? = null,
    @StringRes val errorMessage: Int? = null
)
