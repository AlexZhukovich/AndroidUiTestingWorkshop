package com.alexzh.moodtracker.data.util

import java.lang.Exception

sealed class Result<T> {
    class Success<T>(val data: T): Result<T>()
    class Error<T>(val cause: Exception): Result<T>()
    class Loading<T>: Result<T>()
}