package com.alexzh.moodtracker.data

import com.alexzh.moodtracker.data.exception.ServiceUnavailableException
import com.alexzh.moodtracker.data.exception.Unauthorized
import com.alexzh.moodtracker.data.exception.UserInfoIsNotAvailableException
import com.alexzh.moodtracker.data.remote.model.UserInfoModel
import com.alexzh.moodtracker.data.remote.service.UserRemoteService
import com.alexzh.moodtracker.data.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.ConnectException

class UserRepositoryImpl(
    private val remoteService: UserRemoteService
): UserRepository {

    override fun getUserInfo(): Flow<Result<UserInfoModel>> {
        return flow {
            try {
                emit(Result.Loading())
                val response = remoteService.getUserInfo()
                val userInfo = response.body()
                when {
                    response.code() == 200 && userInfo != null ->
                        emit(Result.Success(userInfo))
                    response.code() == 401 ->
                        emit(Result.Error(Unauthorized()))
                    else ->
                        emit(Result.Error(UserInfoIsNotAvailableException()))
                }
            } catch (ex: ConnectException) {
                emit(Result.Error(ServiceUnavailableException()))
            }
        }
    }
}