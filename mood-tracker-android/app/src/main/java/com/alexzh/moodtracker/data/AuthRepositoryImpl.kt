package com.alexzh.moodtracker.data

import com.alexzh.moodtracker.data.exception.InvalidCredentialsException
import com.alexzh.moodtracker.data.exception.ServiceUnavailableException
import com.alexzh.moodtracker.data.exception.UserAlreadyExistException
import com.alexzh.moodtracker.data.local.session.SessionManager
import com.alexzh.moodtracker.data.model.JwtToken
import com.alexzh.moodtracker.data.remote.model.CreateUserModel
import com.alexzh.moodtracker.data.remote.model.LoginUserModel
import com.alexzh.moodtracker.data.remote.service.UserRemoteService
import com.alexzh.moodtracker.data.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.ConnectException

class AuthRepositoryImpl(
    private val remoteService: UserRemoteService,
    private val sessionManager: SessionManager
): AuthRepository {

    override fun createAccount(
        name: String,
        email: String,
        password: String
    ): Flow<Result<JwtToken>> {
        return flow {
            emit(Result.Loading())
            try {
                val response = remoteService.createUser(
                    CreateUserModel(email, password, name)
                )
                emit(
                    if (response.isSuccessful) {
                        val token = response.body()
                        token?.let {
                            sessionManager.saveToken(JwtToken(it))
                        }

                        Result.Success(JwtToken(token))
                    } else {
                        Result.Error(UserAlreadyExistException())
                    }
                )
            } catch (ex: ConnectException) {
                emit(Result.Error(ServiceUnavailableException()))
            }
        }
    }

    override fun logIn(
        email: String,
        password: String
    ): Flow<Result<JwtToken>> {
        return flow {
            emit(Result.Loading())
            try {
                val response = remoteService.login(LoginUserModel(email, password))
                if (response.isSuccessful) {
                    val token = response.body()
                    token?.let {
                        sessionManager.saveToken(JwtToken(it))
                    }
                    emit(Result.Success(JwtToken(token)))
                } else {
                    emit(Result.Error(InvalidCredentialsException()))
                }
            } catch (ex: ConnectException) {
                emit(Result.Error(ServiceUnavailableException()))
            }
        }
    }

    override suspend fun logOut(): Flow<Result<Unit>> {
        return flow {
            emit(Result.Loading())
            try {
                sessionManager.saveToken(JwtToken(null))
                emit(Result.Success(Unit))
            } catch (ex: ConnectException) {
                emit(Result.Error(ServiceUnavailableException()))
            }
        }
    }
}