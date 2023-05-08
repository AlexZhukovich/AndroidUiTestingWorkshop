package com.alexzh.moodtracker.presentation.feature.auth.createaccount

sealed class CreateAccountEvent {
    object CreateAccount: CreateAccountEvent()

    data class EmailChange(
        val email: String
    ): CreateAccountEvent()

    data class NameChange(
        val name: String
    ): CreateAccountEvent()

    data class PasswordChange(
        val password: String
    ): CreateAccountEvent()
}