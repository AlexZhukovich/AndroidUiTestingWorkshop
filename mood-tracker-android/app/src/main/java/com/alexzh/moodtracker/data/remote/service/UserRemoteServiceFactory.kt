package com.alexzh.moodtracker.data.remote.service

import com.alexzh.moodtracker.data.remote.interceptor.AuthInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UserRemoteServiceFactory {
    companion object {
        const val BASE_URL = "http://10.0.2.2:8080/api/v1/"
    }

    fun createMoodTrackerRemoteService(
        isDebug: Boolean,
        authInterceptor: AuthInterceptor
    ): UserRemoteService {
        val okHttpClient = createOkHttpClient(
            createLoggingInterceptor(isDebug),
            authInterceptor
        )
        val gson = createGson()
        return createMoodTrackerRemoteService(gson, okHttpClient)
    }

    private fun createMoodTrackerRemoteService(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): UserRemoteService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(UserRemoteService::class.java)
    }

    private fun createGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    private fun createOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    private fun createLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}