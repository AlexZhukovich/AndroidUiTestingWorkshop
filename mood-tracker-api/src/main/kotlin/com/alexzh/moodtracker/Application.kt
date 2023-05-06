package com.alexzh.moodtracker

import com.alexzh.moodtracker.data.database.DatabaseConnector
import com.alexzh.moodtracker.plugins.*
import io.ktor.server.application.*
import org.koin.java.KoinJavaComponent.get

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureDependencyInjection()
    configureStatusPages()
    configureAuthentication()
    configureRouting()
    configureContentNegotiation()
    configureSerialization()
    configureHTTP()
    configureMonitoring()

    val databaseConnector: DatabaseConnector = get(DatabaseConnector::class.java)
    databaseConnector.connect()
}
