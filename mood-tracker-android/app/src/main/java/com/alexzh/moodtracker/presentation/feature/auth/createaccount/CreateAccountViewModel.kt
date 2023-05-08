package com.alexzh.moodtracker.presentation.feature.auth.createaccount

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.data.AuthRepository
import com.alexzh.moodtracker.data.exception.ServiceUnavailableException
import com.alexzh.moodtracker.data.exception.UserAlreadyExistException
import com.alexzh.moodtracker.data.util.Result
import com.alexzh.moodtracker.presentation.core.string.isValidEmail
import kotlinx.coroutines.launch
import java.lang.Exception

class CreateAccountViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _state = mutableStateOf(CreateAccountScreenState())
    val state: State<CreateAccountScreenState> = _state

    fun onEvent(event: CreateAccountEvent) {
        when (event) {
            is CreateAccountEvent.CreateAccount -> createAccount()
            is CreateAccountEvent.NameChange -> nameChanged(event.name)
            is CreateAccountEvent.EmailChange -> emailChanged(event.email)
            is CreateAccountEvent.PasswordChange -> passwordChanged(event.password)
        }
    }

    private fun nameChanged(name: String) {
        _state.value = _state.value.copy(
            name = name,
            nameErrorMessage = null,
            errorMessage = null
        )
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

    private fun createAccount() {
        viewModelScope.launch {
            if (_state.value.name.length < 4) {
                _state.value = _state.value.copy(
                    emailErrorMessage = null,
                    nameErrorMessage = R.string.createAccountScreen_error_nameIsTooShort_label,
                    passwordErrorMessage = null,
                    errorMessage = null
                )
                return@launch
            }
            if (_state.value.email.length < 4) {
                _state.value = _state.value.copy(
                    emailErrorMessage = R.string.createAccountScreen_error_emailIsTooShort_label,
                    nameErrorMessage = null,
                    passwordErrorMessage = null,
                    errorMessage = null
                )
                return@launch
            }
            if (!_state.value.email.isValidEmail()) {
                _state.value = _state.value.copy(
                    emailErrorMessage = R.string.genericError_emailIsNotValid_label,
                    nameErrorMessage = null,
                    passwordErrorMessage = null,
                    errorMessage = null
                )
                return@launch
            }
            if (_state.value.password.length < 4) {
                _state.value = _state.value.copy(
                    emailErrorMessage = null,
                    nameErrorMessage = null,
                    passwordErrorMessage = R.string.createAccountScreen_error_passwordIsTooShort_label,
                    errorMessage = null
                )
                return@launch
            }

            authRepository.createAccount(_state.value.name, _state.value.email, _state.value.password).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _state.value = _state.value.copy(
                            loading = true,
                            errorMessage = null,
                            accountCreated = false
                        )
                    }
                    is Result.Success -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            errorMessage = null,
                            accountCreated = true
                        )
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            errorMessage = mapErrorToErrorMessage(result.cause),
                            accountCreated = false
                        )
                    }
                }
            }
        }
    }

    @StringRes
    private fun mapErrorToErrorMessage(ex: Exception): Int {
        return when (ex) {
            is UserAlreadyExistException -> R.string.createAccountScreen_error_userAlreadyExist_label
            is ServiceUnavailableException -> R.string.genericError_serviceUnavailable_label
            else -> R.string.genericError_somethingWentWrong_label
        }
    }
}