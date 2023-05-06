package com.alexzh.moodtracker.di

import com.alexzh.moodtracker.auth.Encryptor
import com.alexzh.moodtracker.auth.EncryptorImpl
import com.alexzh.moodtracker.auth.JwtService
import com.alexzh.moodtracker.auth.JwtServiceImpl
import com.alexzh.moodtracker.controller.AuthController
import com.alexzh.moodtracker.data.UserRepository
import com.alexzh.moodtracker.data.UserRepositoryImpl
import com.alexzh.moodtracker.data.database.DatabaseConnector
import com.alexzh.moodtracker.data.database.H2DatabaseConnector
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("secret")) { System.getenv()["SECRET_KEY"] }

    single<Encryptor> {
        EncryptorImpl(secretKey = get(named("secret")))
    }

    single<JwtService> {
        JwtServiceImpl(secretKey = get(named("secret")))
    }

    single<DatabaseConnector> { H2DatabaseConnector() }
    factory<UserRepository> { UserRepositoryImpl() }

    factory {
        AuthController(
            encryptor = get(),
            jwtService = get(),
            userRepository = get()
        )
    }
}