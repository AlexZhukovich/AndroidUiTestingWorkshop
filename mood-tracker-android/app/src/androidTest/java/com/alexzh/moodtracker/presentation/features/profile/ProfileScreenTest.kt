package com.alexzh.moodtracker.presentation.features.profile

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.data.AuthRepository
import com.alexzh.moodtracker.data.UserRepository
import com.alexzh.moodtracker.data.exception.Unauthorized
import com.alexzh.moodtracker.data.remote.model.UserInfoModel
import com.alexzh.moodtracker.data.util.Result
import com.alexzh.moodtracker.di.appModule
import com.alexzh.moodtracker.di.dataModule
import com.alexzh.moodtracker.presentation.feature.profile.ProfileFragment
import com.karumi.shot.FragmentScenarioUtils.waitForFragment
import io.mockk.every
import io.mockk.mockk
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

@RunWith(AndroidJUnit4::class)
class ProfileScreenTest : KoinTest {

    private val authRepository: AuthRepository = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk(relaxed = true)

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Before
    fun setup() {
        stopKoin()
        startKoin {
            allowOverride(true)
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            modules(
                dataModule,
                appModule,
                module {
                    single { authRepository }
                    single { userRepository }
                }
            )
        }
    }

    /**
     * Create a test case that shows user information when the user is logged in.
     */
    @Test
    fun displayUserInfo_WhenUserIsLoggedIn() {
        val email = "test-email@test-domain.com"
        val username = "Test User"

        every { userRepository.getUserInfo() } returns flowOf(Result.Success(UserInfoModel(email, username)))

        launchFragmentInContainer<ProfileFragment>(
            themeResId = R.style.Theme_MoodTracker
        )


    }

    /**
     * Create a test case that shows "Create Account" and "Login" when the user is not logged in.
     */
    @Test
    fun displayCreateAccountAndLoginOptions_WhenUserIsNotLoggedIn() {
        every { userRepository.getUserInfo() } returns flowOf(Result.Error(Unauthorized()))

        val fragmentScenario = launchFragmentInContainer<ProfileFragment>(
            themeResId = R.style.Theme_MoodTracker
        )
        val context = fragmentScenario.waitForFragment().requireContext()


    }
}