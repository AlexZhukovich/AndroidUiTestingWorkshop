package com.alexzh.moodtracker.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserInfoModel(
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String
)
