package com.alexzh.moodtracker.presentation.features.today.dsl

import androidx.compose.ui.test.junit4.ComposeTestRule

class AddMoodScreenRobot(
    composeTestRule: ComposeTestRule
) : BaseOperations(composeTestRule) {

}

internal fun BaseTest.addMoodScreen(
    func: AddMoodScreenRobot.() -> Unit
) = AddMoodScreenRobot(composeTestRule).also(func)