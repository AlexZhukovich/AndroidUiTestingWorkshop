package com.alexzh.moodtracker.presentation.features.auth.login

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.platform.app.InstrumentationRegistry
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.di.appModule
import com.alexzh.moodtracker.di.dataModule
import com.alexzh.moodtracker.presentation.feature.auth.login.LoginFragment
import com.karumi.shot.FragmentScenarioUtils.waitForFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

@ExperimentalMaterial3Api
class LoginScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Before
    fun setup() {
        stopKoin()
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            modules(dataModule, appModule)
        }
    }

    /**
     * Create a test case that shows the "The 'Email' should be at least four characters long"
     * when the user enters the email, which is shorter than 4 symbols.
     *
     * Additional information:
     * - "The 'Email' should be at least four characters long" => R.string.loginScreen_error_emailIsTooShort_label
     */
    @Test
    fun displayEmailIsTooShortError_whenEnteredEmailIsShorterThanFourSymbols() {
        launchFragmentInContainer<LoginFragment>(
            themeResId = R.style.Theme_MoodTracker
        )

        composeTestRule.apply {
            onNodeWithText("Email")
                .performTextInput("t@t")

            onNodeWithText("LOGIN")
                .performClick()

            onNodeWithText("The 'Email' should be at least four characters long")
                .assertIsDisplayed()
        }
    }

    /**
     * Create a test case that shows the "The 'Password' should be at least four characters long"
     * when the user enters the password, which is shorter than 4 symbols.
     *
     * Additional information:
     * - "The 'Password' should be at least four characters long" => R.string.loginScreen_error_passwordIsTooShort_label
     */
    @Test
    fun displayPasswordIsTooShortError_whenEnteredPasswordIsShorterThanFourSymbols() {
        launchFragmentInContainer<LoginFragment>(
            themeResId = R.style.Theme_MoodTracker
        )

        composeTestRule.apply {
            onNodeWithText("Email")
                .performTextInput("t@t.com")

            onNodeWithText("Password")
                .performTextInput("abc")

            onNodeWithText("LOGIN")
                .performClick()

            onNodeWithText("The 'Password' should be at least four characters long")
                .assertIsDisplayed()
        }
    }
}