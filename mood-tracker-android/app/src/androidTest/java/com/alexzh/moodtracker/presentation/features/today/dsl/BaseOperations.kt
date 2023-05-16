package com.alexzh.moodtracker.presentation.features.today.dsl

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers

open class BaseOperations(
    private val composeTestRule: ComposeTestRule
) {
    fun waitForText(text: String) {
        composeTestRule.apply {
            waitUntil {
                onAllNodesWithText(text)
                    .fetchSemanticsNodes().size == 1
            }
        }
    }

    fun waitForContentDescription(contentDescription: String) {
        composeTestRule.apply {
            waitUntil {
                onAllNodesWithContentDescription(contentDescription)
                    .fetchSemanticsNodes().size == 1
            }
        }
    }

    fun clickOnNodeWithContentDescription(contentDescription: String) {
        composeTestRule.apply {
            onNode(hasContentDescription(contentDescription))
                .performClick()
        }
    }

    fun clickOnNodeWithText(text: String) {
        composeTestRule.apply {
            onNode(hasText(text))
                .performClick()
        }
    }

    fun semanticClickOnNodeWithContentDescription(contentDescription: String) {
        composeTestRule.apply {
            onNode(hasContentDescription(contentDescription))
                .performSemanticsAction(SemanticsActions.OnClick)
        }
    }

    fun enterNote(nodeText: String, note: String) {
        composeTestRule.apply {
            onNodeWithText(nodeText)
                .performTextInput(note)
        }
    }

    fun scrollAndClickOnNodeWithText(text: String) {
        composeTestRule.apply {
            onNode(hasText(text))
                .performScrollTo()
                .performClick()
        }
    }

    fun clickOnToolbarIcon(contentDescription: String) {
        Espresso.onView(ViewMatchers.withContentDescription(contentDescription))
            .perform(ViewActions.click())
    }

    fun hasEmotionStateItem(
        emotionState: String,
        note: String,
        activities: List<String>
    ) {
        composeTestRule.apply {
            onNode(withEmotionStateAndNote(emotionState, note)).apply {
                activities.forEach {
                    this.assert(hasAnyChild(hasText(it)))
                }
            }
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