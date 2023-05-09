package com.alexzh.moodtracker.presentation.features.today.dsl

import androidx.compose.ui.test.junit4.ComposeTestRule

class TodayScreenRobot(
    composeTestRule: ComposeTestRule
) : BaseOperations(composeTestRule) {

    init {
        waitUntilText("Emotions")
    }

    fun addMood() {
        clickOnNodeWithContentDescription("Add")
    }

    fun hasItem(
        emotion: String,
        note: String,
        vararg activity: String
    ) {
        hasEmotionStateWithNote(emotion, note, activity.asList())
    }

    fun selectItemByEmotion(emotion: String) {
        clickWithSemanticActionOnNodeWithContentDescription(emotion)
    }
}

internal fun BaseTest.todayScreen(
    func: TodayScreenRobot.() -> Unit
) = TodayScreenRobot(composeTestRule).apply(func)