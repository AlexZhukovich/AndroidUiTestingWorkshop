package com.alexzh.moodtracker

import android.app.Application
import com.alexzh.moodtracker.di.appModule
import com.alexzh.moodtracker.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MoodTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MoodTrackerApp)
            modules(listOf(appModule, dataModule))
        }
    }
}