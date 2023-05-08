package com.alexzh.moodtracker.data.local.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.fredporciuncula.flow.preferences.Preference

class MoodTrackerPreferenceManager(
    context: Context
) : AppPreferenceManager {

    companion object {
        const val USE_24H_FORMAT_PREFERENCE = "use24hours_preference"
    }

    private val flowSharedPreference = FlowSharedPreferences(
        PreferenceManager.getDefaultSharedPreferences(context)
    )

    override fun is24hFormatActive(): Preference<Boolean> {
        return  flowSharedPreference.getBoolean(USE_24H_FORMAT_PREFERENCE, true)
    }
}