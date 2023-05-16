package com.alexzh.moodtracker.presentation.features.today.dsl

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class TodayScreenE2ETestDsl : BaseTest() {

    /**
     * Create an E2E test case that adds emotional state and render it on the today screen.
     */
    @Test
    fun displayEmotion_WhenEmotionHistoryWasAddedViaAddMoodScreen() {
        val note = UUID.randomUUID().toString()

        launchApp()
        todayScreen {
            addEmotionalState()
        }
        addMoodScreen {
            selectEmotion("Happy")
            selectActivity("Reading", "Gaming")
            enterNote(note)
            save()
        }
        todayScreen {
            hasItem(
                "Happy",
                note,
                "Reading", "Gaming"
            )
            openEmotionalItem("Happy")
        }
        addMoodScreen {
            delete()
        }
    }
}