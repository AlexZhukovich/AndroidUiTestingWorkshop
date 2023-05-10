package com.alexzh.moodtracker.presentation.features.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.annotation.AppScreenshotTest
import com.alexzh.moodtracker.presentation.feature.settings.SettingsFragment
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import sergio.sastre.uitesting.utils.common.UiMode
import sergio.sastre.uitesting.utils.fragmentscenario.FragmentScenarioConfigurator
import sergio.sastre.uitesting.utils.fragmentscenario.waitForFragment
import java.util.Locale

@ExperimentalMaterial3Api
@RunWith(JUnit4::class)
class SettingsScreenScreenshotTest : ScreenshotTest {

    @get:Rule
    val composeEmptyTestRule = createEmptyComposeRule()

    /**
     * Create a test case that takes a screenshot for the light mode.
     */
    @Test @AppScreenshotTest
    fun settingsScreen_light_defaultState() {
        val fragmentScenario = FragmentScenarioConfigurator
            .setUiMode(UiMode.DAY)
            .setTheme(R.style.Theme_MoodTracker)
            .launchInContainer(
                SettingsFragment::class.java
            )

        compareScreenshot(
            fragment = fragmentScenario.waitForFragment(),
            name = "settingsScreen_light"
        )
        fragmentScenario.close()
    }

    /**
     * Create a test case that takes a screenshot for the dark mode.
     */
    @Test @AppScreenshotTest
    fun settingsScreen_dark_defaultState() {
        val fragmentScenario = FragmentScenarioConfigurator
            .setUiMode(UiMode.NIGHT)
            .setTheme(R.style.Theme_MoodTracker)
            .launchInContainer(
                SettingsFragment::class.java
            )

        compareScreenshot(
            fragment = fragmentScenario.waitForFragment(),
            name = "settingsScreen_dark"
        )
        fragmentScenario.close()
    }
}