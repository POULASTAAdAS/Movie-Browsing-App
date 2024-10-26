package com.poulastaa.mflix.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.poulastaa.mflix.MFlix
import com.poulastaa.mflix.core.data.network.RequestInterceptor
import com.poulastaa.mflix.core.data.repository.PreferencesDatastore
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideDatastorePreferences(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = {
            context.preferencesDataStoreFile(name = "AppPreferences")
        }
    )

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        dataStore: DataStore<Preferences>,
    ): DataStoreRepository = PreferencesDatastore(dataStore)


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
        ds: DataStoreRepository,
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(RequestInterceptor(ds))
        .cookieJar(JavaNetCookieJar(cookieManager))
        .build()
}