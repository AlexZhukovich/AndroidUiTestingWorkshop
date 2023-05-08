package com.alexzh.moodtracker.presentation.feature.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.alexzh.moodtracker.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}