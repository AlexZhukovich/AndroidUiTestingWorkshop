package com.alexzh.moodtracker.presentation.component.loadingbutton

import androidx.compose.ui.test.junit4.createComposeRule
import com.alexzh.moodtracker.annotation.AppScreenshotTest
import com.alexzh.moodtracker.presentation.component.LoadingButton
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LoadingButtonAnimationTest : ScreenshotTest {

    @get:Rule
    val composableTestRule = createComposeRule()

    /**
     * Create a test case that verifies the loading animation of the "LoadingButton".
     * We can test animation by taking multiple screenshots during animation.
     *
     * The "Compose UI Test" library allows us to manage the virtual clock.
     * It means that we can start and pause the virtual clock whenever we want.
     *
     * Guide: https://alexzh.com/jetpack-compose-testing-animations/
     */
    @Test @AppScreenshotTest
    fun loadingButton_loadingAnimation() {
        composableTestRule.apply {
            setContent {
                mainClock.autoAdvance = false
                LoadingButton(
                    text = "Some text",
                    isLoading = true,
                    onClick = {},
                )
            }

            compareScreenshot(composableTestRule, "loadingButton_loadingAnimation_state0")
            mainClock.advanceTimeBy(100)
            compareScreenshot(composableTestRule, "loadingButton_loadingAnimation_state1")
            mainClock.advanceTimeBy(200)
            compareScreenshot(composableTestRule, "loadingButton_loadingAnimation_state2")
            mainClock.advanceTimeBy(300)
            compareScreenshot(composableTestRule, "loadingButton_loadingAnimation_state3")
            mainClock.advanceTimeBy(400)
            compareScreenshot(composableTestRule, "loadingButton_loadingAnimation_state4")
        }
    }
}