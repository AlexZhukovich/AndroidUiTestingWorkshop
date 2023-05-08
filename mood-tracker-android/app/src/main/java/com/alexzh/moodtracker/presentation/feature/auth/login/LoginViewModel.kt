package com.alexzh.moodtracker.presentation.feature.auth.login

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.data.AuthRepository
import com.alexzh.moodtracker.data.exception.InvalidCredentialsException
import com.alexzh.moodtracker.data.exception.ServiceUnavailableException
import com.alexzh.moodtracker.data.util.Result
import com.alexzh.moodtracker.presentation.core.string.isValidEmail
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _state = mutableStateOf(LoginScreenState())
    val state: State<LoginScreenState> = _state

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login()
            is LoginEvent.EmailChange -> emailChanged(event.email)
            is LoginEvent.PasswordChange -> passwordChanged(event.password)
        }
    }

    private fun emailChanged(email: String) {
        _state.value = _state.value.copy(
            email = email,
            emailErrorMessage = null,
            errorMessage = null
        )
    }

    private fun passwordChanged(password: String) {
        _state.value = _state.value.copy(
            password = password,
            passwordErrorMessage = null,
            errorMessage = null
        )
    }

    private fun login() {
        viewModelScope.launch {
            if (_state.value.email.length < 4) {
                _state.value = _state.value.copy(
                    emailErrorMessage = R.string.loginScreen_error_emailIsTooShort_label,
                    passwordErrorMessage = null,
                    errorMessage = null
                )
                return@launch
            }
            if (!_state.value.email.isValidEmail()) {
                _state.value = _state.value.copy(
                    emailErrorMessage = R.string.genericError_emailIsNotValid_label,
                    passwordErrorMessage = null,
                    errorMessage = null
                )
                return@launch
            }
            if (_state.value.password.length < 4) {
                _state.value = _state.value.copy(
                    emailErrorMessage = null,
                    passwordErrorMessage = R.string.loginScreen_error_passwordIsTooShort_label,
                    errorMessage = null
                )
                return@launch
            }

            authRepository.logIn(_state.value.email, _state.value.password).collect { result ->
                when(result) {
                    is Result.Loading -> {
                        _state.value = _state.value.copy(
                            loading = true,
                            errorMessage = null,
                            userLoggedIn = false
                        )
                    }
                    is Result.Success -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            errorMessage = null,
                            userLoggedIn = true
                        )
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            errorMessage = mapErrorToErrorMessage(result.cause),
                            userLoggedIn = false
                        )
                    }
                }
            }
        }
    }

    @StringRes
    private fun mapErrorToErrorMessage(ex: Exception): Int {
        return when (ex) {
            is ServiceUnavailableException -> R.string.genericError_serviceUnavailable_label
            is InvalidCredentialsException -> R.string.loginScreen_error_invalidCredentials_label
            else -> R.string.genericError_somethingWentWrong_label
        }
    }
}