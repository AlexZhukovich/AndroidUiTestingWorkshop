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
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import com.karumi.shot.ActivityScenarioUtils.waitForActivity
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import sergio.sastre.uitesting.utils.activityscenario.ActivityScenarioConfigurator
import sergio.sastre.uitesting.utils.common.UiMode
import java.time.LocalDate

@ExperimentalMaterial3Api
@RunWith(TestParameterInjector::class)
class WeekCalendarParamScreenshotTest(
    @TestParameter val uiMode: UiMode,
    @TestParameter val todayIsSelectedDate: Boolean
) : ScreenshotTest {
    private val testDate = LocalDate.of(2023, 5, 10)

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    /**
     * Create a test case that generates 4 screenshots for the following parameters:
     * - UiMode: NIGHT, DAY
     * - State: "todayDate == selectedDate", "todayDate != selectedDate"
     */
    @Test @AppScreenshotTest
    fun weekCalendar_paramUiModeAndSelectedDate() {

    }
}