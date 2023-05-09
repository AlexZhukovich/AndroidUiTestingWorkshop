package com.alexzh.moodtracker.presentation.features.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.annotation.AppScreenshotTest
import com.alexzh.moodtracker.presentation.feature.settings.SettingsFragment
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import sergio.sastre.uitesting.utils.common.FontSize
import sergio.sastre.uitesting.utils.common.UiMode
import sergio.sastre.uitesting.utils.fragmentscenario.FragmentScenarioConfigurator
import sergio.sastre.uitesting.utils.fragmentscenario.waitForFragment

@ExperimentalMaterial3Api
@RunWith(TestParameterInjector::class)
class SettingsScreenParamScreenshotTest(
    @TestParameter val uiMode: UiMode,
    @TestParameter val fontSize: FontSize
) : ScreenshotTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    /**
     * Create a test case that generates 8 screenshots for the following scenarios:
     * - UiMode: NIGHT, DAY
     * - Font size: SMALL, NORMAL, LARGE, HUGE
     */
    @Test @AppScreenshotTest
    fun settingsScreen_customFontSizeAnUiMode() {
        val fragmentScenario = FragmentScenarioConfigurator
            .setUiMode(uiMode)
            .setFontSize(fontSize)
            .setTheme(R.style.Theme_MoodTracker)
            .launchInContainer(
                SettingsFragment::class.java
            )

        compareScreenshot(
            fragment = fragmentScenario.waitForFragment(),
            name = "settingsScreen_${uiMode}_${fontSize}"
        )
        fragmentScenario.close()
    }
}