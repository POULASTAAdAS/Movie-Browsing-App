package com.poulastaa.mflix.di

import android.app.Application
import com.poulastaa.mflix.MFlix
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import java.net.CookieManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCoroutineScope(
        application: Application,
    ): CoroutineScope = (application as MFlix).applicationScope()

    @Provides
    @Singleton
    fun provideCookieManager(): CookieManager = CookieManager()

    @Provides
    @Singleton
    fun provideHttClient(
        cookieManager: CookieManager,
//        ds: DataStoreRepository,
    ): OkHttpClient = OkHttpClient
        .Builder()
//        .addInterceptor(AuthHeaderInterceptor(ds = ds))
        .cookieJar(JavaNetCookieJar(cookieManager))
        .build()
}