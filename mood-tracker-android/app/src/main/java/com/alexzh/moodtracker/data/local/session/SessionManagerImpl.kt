package com.alexzh.moodtracker.data.local.session

import android.content.SharedPreferences
import androidx.core.content.edit
import com.alexzh.moodtracker.data.model.JwtToken

class SessionManagerImpl(
    private val sharedPreferences: SharedPreferences
): SessionManager {
    companion object {
        private const val KEY_TOKEN = "auth_token"
    }

    override fun getToken(): JwtToken = JwtToken(sharedPreferences.getString(KEY_TOKEN, null))

    override fun saveToken(token: JwtToken) {
        sharedPreferences.edit(commit = true) {
            putString(KEY_TOKEN, token.value)
        }
    }
}