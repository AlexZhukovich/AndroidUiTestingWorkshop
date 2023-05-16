package com.alexzh.moodtracker.presentation.features.today.dsl

import androidx.compose.ui.test.junit4.ComposeTestRule

class AddMoodScreenRobot(
    composeTestRule: ComposeTestRule
) : BaseOperations(composeTestRule) {

    init {
        waitForContentDescription("Happy")
    }

    fun selectEmotion(emotion: String) {
        clickOnNodeWithContentDescription(emotion)
    }

    fun selectActivity(vararg activities: String) {
        activities.forEach {
            clickOnNodeWithText(it)
        }
    }

    fun enterNote(text: String) {
        enterNote("Note", text)
    }

    fun save() {
        scrollAndClickOnNodeWithText("Save")
    }

    fun delete() {
        clickOnToolbarIcon("Delete")
    }
}

internal fun BaseTest.addMoodScreen(
    func: AddMoodScreenRobot.() -> Unit
) = AddMoodScreenRobot(composeTestRule).also(func)