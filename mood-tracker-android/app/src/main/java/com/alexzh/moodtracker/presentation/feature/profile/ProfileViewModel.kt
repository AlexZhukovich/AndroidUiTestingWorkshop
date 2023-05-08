package com.alexzh.moodtracker.presentation.feature.profile

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.data.AuthRepository
import com.alexzh.moodtracker.data.UserRepository
import com.alexzh.moodtracker.data.exception.ServiceUnavailableException
import com.alexzh.moodtracker.data.exception.Unauthorized
import com.alexzh.moodtracker.data.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _state = mutableStateOf(ProfileScreenState())
    val state: State<ProfileScreenState> = _state

    private val _isLoggedInUser = MutableStateFlow(false)
    val isLoggedInUser: Flow<Boolean> = _isLoggedInUser

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.GetUserInfo -> getUserInfo()
            is ProfileEvent.LogOut -> logout()
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            userRepository.getUserInfo().collect { result ->
                when(result) {
                    is Result.Loading -> {
                        _state.value = _state.value.copy(
                            loading = true,
                            userInfoModel = null,
                            errorMessage = null
                        )
                        _isLoggedInUser.emit(false)
                        println("ProfileViewModel => GET_USER_INFO => LOADING...")
                    }
                    is Result.Success -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            userInfoModel = result.data
                        )
                        _isLoggedInUser.emit(true)
                        println("ProfileViewModel => GET_USER_INFO => SUCCESS => ${result.data}")
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            errorMessage = mapErrorToErrorMessage(result.cause)
                        )
                        _isLoggedInUser.emit(false)
                        println("ProfileViewModel => GET_USER_INFO => ERROR => ${result.cause}")
                    }
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            authRepository.logOut().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _state.value = _state.value.copy(
                            loading = true,
                            userInfoModel = null,
                            errorMessage = null
                        )
                        _isLoggedInUser.emit(true)
                        println("ProfileViewModel => LOGOUT => LOADING...")
                    }
                    is Result.Success -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            userInfoModel = null
                        )
                        _isLoggedInUser.emit(false)
                        println("ProfileViewModel => LOGOUT => SUCCESS")
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            errorMessage = mapErrorToErrorMessage(result.cause)
                        )
                        _isLoggedInUser.emit(true)
                        println("ProfileViewModel => LOGOUT => ERROR => ${result.cause}")
                    }
                }
            }
        }
    }

    @StringRes
    private fun mapErrorToErrorMessage(error: Exception): Int {
        return when(error) {
            is ServiceUnavailableException -> R.string.genericError_serviceUnavailable_label
            is Unauthorized ->R.string.genericError_unauthorizedUser_label
            else -> R.string.genericError_somethingWentWrong_label
        }
    }
}