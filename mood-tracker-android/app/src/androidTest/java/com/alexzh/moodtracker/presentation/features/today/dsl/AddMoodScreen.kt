package com.alexzh.moodtracker.presentation.features.today.dsl

import androidx.compose.ui.test.junit4.ComposeTestRule

class AddMoodScreenRobot(
    composeTestRule: ComposeTestRule
) : BaseOperations(composeTestRule) {

    init {
        waitUntilContentDescription("Happy")
    }

    fun selectMood(mood: String) {
        clickOnNodeWithContentDescription(mood)
    }

    fun selectActivities(vararg activities: String) {
        activities.forEach {
            clickOnNodeWithText(it)
        }
    }

    fun enterNote(text: String) {
        enterTextOnNodeWithText("Note", text)
    }

    fun saveNote() {
        scrollAndClickOnNodeWithText("Save")
    }

    fun delete() {
        clickOnToolbarItemWithContentDescription("Delete")
    }
}

internal fun BaseTest.addMoodScreen(
    func: AddMoodScreenRobot.() -> Unit
) = AddMoodScreenRobot(composeTestRule).also(func)