package com.alexzh.moodtracker.data.database

import org.jetbrains.exposed.sql.Database

interface DatabaseConnector {

    fun connect(): Database
}