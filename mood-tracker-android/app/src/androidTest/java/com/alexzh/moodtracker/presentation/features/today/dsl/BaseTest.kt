package com.alexzh.moodtracker.presentation.features.today.dsl

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.test.core.app.ActivityScenario
import com.alexzh.moodtracker.presentation.feature.home.HomeActivity
import org.junit.Rule

open class BaseTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    fun launchApp() {
        ActivityScenario.launch(HomeActivity::class.java)
    }
}