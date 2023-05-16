package com.alexzh.moodtracker.presentation.features.today.dsl

import androidx.compose.ui.test.junit4.ComposeTestRule

class TodayScreenRobot(
    composeTestRule: ComposeTestRule
) : BaseOperations(composeTestRule) {

    init {
        waitForText("Emotions")
    }

    fun addEmotionalState() {
        clickOnNodeWithContentDescription("Add")
    }

    fun hasItem(
        emotionalState: String,
        note: String,
        vararg activities: String
    ) {
        hasEmotionStateItem(emotionalState, note, activities.asList())
    }

    fun openEmotionalItem(
        emotionalState: String,
    ) {
        semanticClickOnNodeWithContentDescription(emotionalState)
    }
}

internal fun BaseTest.todayScreen(
    func: TodayScreenRobot.() -> Unit
) = TodayScreenRobot(composeTestRule).apply(func)