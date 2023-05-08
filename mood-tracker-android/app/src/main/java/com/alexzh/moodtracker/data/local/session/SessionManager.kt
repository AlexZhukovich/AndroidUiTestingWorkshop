package com.alexzh.moodtracker.data.local.session

import com.alexzh.moodtracker.data.model.JwtToken

interface SessionManager {

    fun getToken(): JwtToken

    fun saveToken(token: JwtToken)
}