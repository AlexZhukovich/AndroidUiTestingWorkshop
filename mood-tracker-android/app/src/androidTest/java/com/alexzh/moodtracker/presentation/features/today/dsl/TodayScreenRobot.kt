package com.alexzh.moodtracker.presentation.features.today.dsl

import androidx.compose.ui.test.junit4.ComposeTestRule

class TodayScreenRobot(
    composeTestRule: ComposeTestRule
) : BaseOperations(composeTestRule) {

}

internal fun BaseTest.todayScreen(
    func: TodayScreenRobot.() -> Unit
) = TodayScreenRobot(composeTestRule).apply(func)