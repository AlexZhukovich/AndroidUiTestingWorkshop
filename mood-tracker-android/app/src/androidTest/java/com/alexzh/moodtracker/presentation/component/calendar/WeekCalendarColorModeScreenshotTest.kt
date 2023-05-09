package com.alexzh.moodtracker.presentation.component.calendar

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.annotation.AppScreenshotTest
import com.alexzh.moodtracker.presentation.theme.AppTheme
import com.karumi.shot.ActivityScenarioUtils.waitForActivity
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import sergio.sastre.uitesting.utils.activityscenario.ActivityScenarioConfigurator
import sergio.sastre.uitesting.utils.common.UiMode
import java.time.LocalDate

@ExperimentalMaterial3Api
@RunWith(JUnit4::class)
class WeekCalendarColorModeScreenshotTest : ScreenshotTest {
    private val testDate = LocalDate.of(2023, 5, 10)

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    /**
     * Create a test case that verifies how the "WeekCalendar" components looks.
     * Requirements:
     * - State: todayDate == selectedDate
     * - Color scheme: light
     */
    @Test @AppScreenshotTest
    fun weekCalendar_light_todayInSelectedDate() {
        val activityScenario = ActivityScenarioConfigurator.ForComposable()
            .setUiMode(UiMode.DAY)
            .launchConfiguredActivity()
            .onActivity {
                it.setContent {
                    composeTestRule.mainClock.autoAdvance = false
                    AppTheme {
                        Box(
                            modifier = Modifier.size(400.dp, height = 200.dp)
                                .background(MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.Center
                        ) {
                            WeekCalendar(
                                startDate = testDate.minusDays(6),
                                selectedDate = testDate.minusDays(1),
                                onSelectedDateChange = {},
                                todayDate = testDate.minusDays(1)
                            )
                        }
                    }
                }
            }

        composeTestRule.mainClock.advanceTimeBy(400)
        activityScenario.waitForActivity()
        compareScreenshot(composeTestRule, "weekCalendar_light_todayInSelectedDate")

        activityScenario.close()
    }

    /**
     * Create a test case that verifies how the "WeekCalendar" components looks.
     * Requirements:
     * - State: todayDate == selectedDate
     * - Color scheme: dark
     */
    @Test @AppScreenshotTest
    fun weekCalendar_dark_todayInSelectedDate() {
        val activityScenario = ActivityScenarioConfigurator.ForComposable()
            .setUiMode(UiMode.NIGHT)
            .launchConfiguredActivity()
            .onActivity {
                it.setContent {
                    composeTestRule.mainClock.autoAdvance = false
                    AppTheme {
                        Box(
                            modifier = Modifier.size(400.dp, height = 200.dp)
                                .background(MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.Center
                        ) {
                            WeekCalendar(
                                startDate = testDate.minusDays(6),
                                selectedDate = testDate.minusDays(1),
                                onSelectedDateChange = {},
                                todayDate = testDate.minusDays(1)
                            )
                        }
                    }
                }
            }

        composeTestRule.mainClock.advanceTimeBy(400)
        activityScenario.waitForActivity()
        compareScreenshot(composeTestRule, "weekCalendar_dark_todayInSelectedDate")

        activityScenario.close()
    }

    /**
     * Create a test case that verifies how the "WeekCalendar" components looks.
     * Requirements:
     * - State: todayDate != selectedDate
     * - Color scheme: light
     */
    @Test @AppScreenshotTest
    fun weekCalendar_light_todayInNotSelectedDate() {
        val activityScenario = ActivityScenarioConfigurator.ForComposable()
            .setUiMode(UiMode.DAY)
            .launchConfiguredActivity()
            .onActivity {
                it.setContent {
                    composeTestRule.mainClock.autoAdvance = false
                    AppTheme {
                        Box(
                            modifier = Modifier.size(400.dp, height = 200.dp)
                                .background(MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.Center
                        ) {
                            WeekCalendar(
                                startDate = testDate.minusDays(6),
                                selectedDate = testDate.minusDays(1),
                                onSelectedDateChange = {},
                                todayDate = testDate.minusDays(2)
                            )
                        }
                    }
                }
            }

        composeTestRule.mainClock.advanceTimeBy(400)
        activityScenario.waitForActivity()
        compareScreenshot(composeTestRule, "weekCalendar_light_todayInNotSelectedDate")

        activityScenario.close()
    }

    /**
     * Create a test case that verifies how the "WeekCalendar" components looks.
     * Requirements:
     * - State: todayDate != selectedDate
     * - Color scheme: dark
     */
    @Test @AppScreenshotTest
    fun weekCalendar_dark_todayInNotSelectedDate() {
        val activityScenario = ActivityScenarioConfigurator.ForComposable()
            .setUiMode(UiMode.NIGHT)
            .launchConfiguredActivity()
            .onActivity {
                it.setContent {
                    composeTestRule.mainClock.autoAdvance = false
                    AppTheme {
                        Box(
                            modifier = Modifier.size(400.dp, height = 200.dp)
                                .background(MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.Center
                        ) {
                            WeekCalendar(
                                startDate = testDate.minusDays(6),
                                selectedDate = testDate.minusDays(1),
                                onSelectedDateChange = {},
                                todayDate = testDate.minusDays(2)
                            )
                        }
                    }
                }
            }

        composeTestRule.mainClock.advanceTimeBy(400)
        activityScenario.waitForActivity()
        compareScreenshot(composeTestRule, "weekCalendar_dark_todayInNotSelectedDate")

        activityScenario.close()
    }
}