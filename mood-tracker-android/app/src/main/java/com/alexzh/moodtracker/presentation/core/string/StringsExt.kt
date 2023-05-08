package com.alexzh.moodtracker.presentation.core.string

import android.text.TextUtils

fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) &&
        android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}