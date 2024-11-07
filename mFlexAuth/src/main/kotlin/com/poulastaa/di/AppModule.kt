package com.poulastaa.di

import com.poulastaa.core.repository.AuthRepository
import com.poulastaa.core.repository.DatabaseRepository
import com.poulastaa.data.repository.AuthRepoImpl
import com.poulastaa.data.repository.DatabaseRepoImpl
import io.ktor.client.HttpClient
import org.koin.dsl.bind
import org.koin.dsl.module

fun provideRepository() = module {
    single<DatabaseRepoImpl> {
        DatabaseRepoImpl()
    }.bind<DatabaseRepository>()

    single<AuthRepository> {
        AuthRepoImpl(
            db = get()
        )
    }.bind<AuthRepository>()
}

fun provideHttClient() = module {
    single<HttpClient> {
        HttpClient()
    }
}