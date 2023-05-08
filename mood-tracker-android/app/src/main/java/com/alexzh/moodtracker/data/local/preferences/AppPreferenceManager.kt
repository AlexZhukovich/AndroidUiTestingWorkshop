package com.alexzh.moodtracker.data.local.preferences

import com.fredporciuncula.flow.preferences.Preference

interface AppPreferenceManager {

    fun is24hFormatActive(): Preference<Boolean>
}