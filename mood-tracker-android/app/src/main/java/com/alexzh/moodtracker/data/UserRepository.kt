package com.alexzh.moodtracker.data

import com.alexzh.moodtracker.data.remote.model.UserInfoModel
import com.alexzh.moodtracker.data.util.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserInfo(): Flow<Result<UserInfoModel>>
}