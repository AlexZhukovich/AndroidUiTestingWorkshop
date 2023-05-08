package com.alexzh.moodtracker.presentation.feature.auth.createaccount

import androidx.annotation.StringRes

data class CreateAccountScreenState(
    val loading: Boolean = false,
    val name: String = "",
    @StringRes val nameErrorMessage: Int? = null,
    val email: String = "",
    @StringRes val emailErrorMessage: Int? = null,
    val password: String = "",
    @StringRes val passwordErrorMessage: Int? = null,
    @StringRes val errorMessage: Int? = null,
    val accountCreated: Boolean = false
)