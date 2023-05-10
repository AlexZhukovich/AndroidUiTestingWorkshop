package com.alexzh.moodtracker.presentation.features.today

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.annotation.AppScreenshotTest
import com.alexzh.moodtracker.data.EmotionHistoryRepository
import com.alexzh.moodtracker.data.util.Result
import com.alexzh.moodtracker.di.appModule
import com.alexzh.moodtracker.di.dataModule
import com.alexzh.moodtracker.presentation.core.date.DateProvider
import com.alexzh.moodtracker.presentation.core.date.DateProviderImpl
import com.alexzh.moodtracker.presentation.feature.today.TodayFragment
import com.alexzh.moodtracker.testdata.EmotionHistoryTestData
import com.karumi.shot.FragmentScenarioUtils.waitForFragment
import com.karumi.shot.ScreenshotTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import sergio.sastre.uitesting.utils.fragmentscenario.waitForFragment
import java.time.LocalDate

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TodayScreenTest : KoinTest, ScreenshotTest {
    private val emotionHistoryRepo = mockk<EmotionHistoryRepository>(relaxed = true)
    private val testDate = LocalDate.of(2023, 5, 10)

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Before
    fun setUp() {
        stopKoin()
        startKoin {
            allowOverride(true)
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            modules(
                listOf(
                    appModule,
                    dataModule,
                    module {
                        single { emotionHistoryRepo }
                        single<DateProvider> { DateProviderImpl(testDate) }
                    }
                )
            )
        }
    }

    /**
     * Create a test case that shows a single emotional state item on the screen.
     */
    @Test
    fun displaySuccessWithSimpleItem_whenDataIsAvailable() {
        every { emotionHistoryRepo.getEmotionsHistoryByDate(any(), any()) } returns
            flowOf(Result.Success(listOf(EmotionHistoryTestData.EMOTION_HISTORY_ITEM(testDate))))

        launchFragmentInContainer<TodayFragment>(
            themeResId = R.style.Theme_MoodTracker
        )

        composeTestRule.apply {
            onRoot().printToLog("MERGED")

            onNode(withEmotionStateAndNote("Excited", "Test note"))
                .assert(hasAnyChild(hasText("Work")))
        }
    }

    /**
     * Create a test case that shows multiple emotional state items on the screen.
     */
    @Test @AppScreenshotTest
    fun displaySuccessWithMultipleItems_whenDataIsAvailable() {
        every { emotionHistoryRepo.getEmotionsHistoryByDate(any(), any()) } returns
                flowOf(Result.Success(EmotionHistoryTestData.EMOTION_HISTORY_ITEMS(testDate)))

        val fragmentScenario = launchFragmentInContainer<TodayFragment>(
            themeResId = R.style.Theme_MoodTracker
        )

        composeTestRule.apply {
            onNode(withEmotionStateAndNote("Excited", "Test note 1"))
                .assert(hasAnyChild(hasText("Work")))

            onNode(withEmotionStateAndNote("Neutral", "Test note 2"))
                .assert(hasAnyChild(hasText("Work")))
                .assert(hasAnyChild(hasText("Shopping")))
        }

        compareScreenshot(
            fragment = fragmentScenario.waitForFragment(),
            name = "todayScreen_multipleItems"
        )
    }

    /**
     * Create a test case that shows the empty state of the screen.
     */
    @Test
    fun displayEmptyState_whenDataIsNotAvailable() {
        every { emotionHistoryRepo.getEmotionsHistoryByDate(any(), any()) } returns
                flowOf(Result.Success(emptyList()))

        val fragmentScenario = launchFragmentInContainer<TodayFragment>(
            themeResId = R.style.Theme_MoodTracker
        )

        composeTestRule.apply {
            onNodeWithText("No data")
                .assertIsDisplayed()
        }
        compareScreenshot(
            fragment = fragmentScenario.waitForFragment(),
            name = "todayScreen_noData"
        )
    }

    private fun withEmotionStateAndNote(
        emotionState: String,
        note: String
    ): SemanticsMatcher {
        return hasText(note)
            .and(hasContentDescription(emotionState))
    }
}