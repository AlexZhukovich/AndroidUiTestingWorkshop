package com.alexzh.moodtracker.data.local.session

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object AuthSharedPreferenceFactory {
    private const val SESSIONS_PREF_FILE_NAME = "auth_prefs"

    fun createAuthSharedPreferences(context: Context): SharedPreferences =
        EncryptedSharedPreferences.create(
            SESSIONS_PREF_FILE_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
}