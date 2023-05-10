package com.alexzh.moodtracker.presentation.features.today

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import com.alexzh.moodtracker.presentation.feature.home.HomeActivity
import io.mockk.every
import org.junit.Rule
import org.junit.Test
import java.util.UUID

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
class TodayScreenE2ETest {

    @get:Rule
    val composableTestRule = createComposeRule()

    /**
     * Create an E2E test case that adds emotional state and render it on the today screen.
     */
    @Test
    fun displayEmotion_WhenEmotionHistoryWasAddedViaAddMoodScreen() {
        val note = UUID.randomUUID().toString()

        ActivityScenario.launch(HomeActivity::class.java)
        composableTestRule.apply {
            waitUntil {
                onAllNodesWithText("Emotions")
                    .fetchSemanticsNodes().size == 1
            }

            onNode(hasContentDescription("Add"))
                .performClick()

            waitUntil {
                onAllNodesWithContentDescription("Happy")
                    .fetchSemanticsNodes().size == 1
            }

            onNodeWithContentDescription("Happy")
                .performClick()

            onNodeWithText("Reading")
                .performClick()

            onNodeWithText("Gaming")
                .performClick()

            onNodeWithText("Note")
                .performTextInput(note)

            onNode(hasText("Save"))
                .performScrollTo()
                .performClick()

            waitUntil {
                onAllNodesWithText("Emotions")
                    .fetchSemanticsNodes().size == 1
            }

            onNode(withEmotionStateAndNote("Happy", note))
                .assert(hasAnyChild(hasText("Reading")))
                .assert(hasAnyChild(hasText("Gaming")))

            onNode(hasContentDescription("Happy"))
                .performSemanticsAction(SemanticsActions.OnClick)

            waitUntil {
                onAllNodesWithContentDescription("Happy")
                    .fetchSemanticsNodes().size == 1
            }

            onView(withContentDescription("Delete"))
                .perform(click())
        }
    }

    private fun withEmotionStateAndNote(
        emotionState: String,
        note: String
    ): SemanticsMatcher {
        return hasText(note)
            .and(hasContentDescription(emotionState))
    }
}