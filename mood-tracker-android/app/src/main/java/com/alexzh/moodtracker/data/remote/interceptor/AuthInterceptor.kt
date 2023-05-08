package com.alexzh.moodtracker.data.remote.interceptor

import com.alexzh.moodtracker.data.local.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val sessionManager: SessionManager
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authRequest = chain.request().newBuilder().apply {
            sessionManager.getToken().value?.let { header("Authorization", "Bearer $it") }
        }.build()
        return chain.proceed(authRequest)
    }
}