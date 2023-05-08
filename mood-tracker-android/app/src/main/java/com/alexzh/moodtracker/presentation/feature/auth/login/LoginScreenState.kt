package com.alexzh.moodtracker.presentation.feature.auth.login

import androidx.annotation.StringRes

data class LoginScreenState(
    val loading: Boolean = false,
    val email: String = "",
    @StringRes val emailErrorMessage: Int? = null,
    val password: String = "",
    @StringRes val passwordErrorMessage: Int? = null,
    val userLoggedIn: Boolean = false,
    @StringRes val errorMessage: Int? = null
)