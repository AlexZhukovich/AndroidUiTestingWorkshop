package com.alexzh.moodtracker.presentation.component.loadingbutton

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.annotation.AppScreenshotTest
import com.alexzh.moodtracker.presentation.component.LoadingButton
import com.alexzh.moodtracker.presentation.theme.AppTheme
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import com.karumi.shot.ActivityScenarioUtils.waitForActivity
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import sergio.sastre.uitesting.utils.activityscenario.ActivityScenarioConfigurator
import sergio.sastre.uitesting.utils.common.DisplaySize
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
        val nameDescription = if (isLoading) "loadingState" else "defaultState"

        val activityScenario = ActivityScenarioConfigurator.ForComposable()
            .setUiMode(uiMode)
            .launchConfiguredActivity()
            .onActivity {
                it.setContent {
                    AppTheme {
                        Box(
                            modifier = Modifier.size(300.dp, height = 100.dp)
                                .background(Color(51,50,51)),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingButton(
                                modifier = Modifier.width(250.dp),
                                text = title.text,
                                isLoading = isLoading,
                                onClick = { }
                            )
                        }
                    }
                }
            }
        activityScenario.waitForActivity()
        compareScreenshot(composeTestRule, "loadingButton_${uiMode}_${title}_${nameDescription}")

        activityScenario.close()
    }

    enum class ButtonTitle(val text: String) {
        SHORT("short name"),
        LONG("super long super long super long  super long  super long ")
    }
}