package com.alexzh.moodtracker.presentation.component.loadingbutton

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import com.alexzh.moodtracker.annotation.AppScreenshotTest
import com.alexzh.moodtracker.presentation.component.LoadingButton
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import com.karumi.shot.ActivityScenarioUtils.waitForActivity
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import sergio.sastre.uitesting.utils.activityscenario.ActivityScenarioConfigurator
import sergio.sastre.uitesting.utils.common.UiMode

@RunWith(TestParameterInjector::class)
class LoadingButtonParamScreenshotTest(
    @TestParameter val uiMode: UiMode,
    @TestParameter val isLoading: Boolean,
    @TestParameter val title: ButtonTitle
) : ScreenshotTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    /**
     * Create a test case that generates 8 screenshots for the following parameters:
     * - UiMode: NIGHT, DAY
     * - State: loading, default (not loading)
     * - Title: short, long
     */
    @Test @AppScreenshotTest
    fun loadingButton_customUiModeAndLoadingStateAndTitle() {

    }

    enum class ButtonTitle(val text: String) {
        SHORT("short name"),
        LONG("super long super long super long  super long  super long ")
    }
}