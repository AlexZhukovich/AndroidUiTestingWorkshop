package com.alexzh.moodtracker.presentation.features.today.dsl

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodayScreenE2ETestDsl : BaseTest() {

    /**
     * Create an E2E test case that adds emotional state and render it on the today screen.
     */
    @Test
    fun displayEmotion_WhenEmotionHistoryWasAddedViaAddMoodScreen() {
        launchApp()
        todayScreen {
            addMood()
        }
        addMoodScreen {
            selectMood("Happy")
            selectActivities("Reading", "Gaming")
            enterNote("Test note")
            saveNote()
        }
        todayScreen {
            hasItem(
                emotion = "Happy",
                note = "Test note",
                "Reading", "Gaming"
            )
            selectItemByEmotion("Happy")
        }
        addMoodScreen {
            delete()
        }
    }
}