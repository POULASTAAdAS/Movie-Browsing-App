package com.poulastaa.mflix.di

import com.poulastaa.mflix.auth.data.repository.AuthRepositoryImpl
import com.poulastaa.mflix.auth.network.repository.OkHttpAuthDataSource
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.domain.repository.auth.AuthRepository
import com.poulastaa.mflix.core.domain.repository.auth.RemoteAuthDataSource
import com.poulastaa.mflix.core.domain.repository.home.HomeRepository
import com.poulastaa.mflix.core.domain.repository.home.RemoteHomeDataSource
import com.poulastaa.mflix.home.data.repository.OnlineFirstHomeRepository
import com.poulastaa.mflix.home.network.HomeMorePagerSource
import com.poulastaa.mflix.home.network.OkHttpRemoteHomeDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import java.net.CookieManager

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideRemoteAuthDatasource(
        client: OkHttpClient
    ): RemoteAuthDataSource = OkHttpAuthDataSource(client)

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        cookieManager: CookieManager,
        ds: DataStoreRepository,
        scope: CoroutineScope,
        remote: RemoteAuthDataSource
    ): AuthRepository = AuthRepositoryImpl(
        cookieManager = cookieManager,
        ds = ds,
        scope = scope,
        remote = remote
    )

    @Provides
    @ViewModelScoped
    fun provideHomeMorePagerSource(
        client: OkHttpClient,
        ds: DataStoreRepository
    ): HomeMorePagerSource = HomeMorePagerSource(client, ds)

    @Provides
    @ViewModelScoped
    fun provideHomeRemoteDataSource(
        client: OkHttpClient,
        pager: HomeMorePagerSource
    ): RemoteHomeDataSource = OkHttpRemoteHomeDataSource(client, pager)

    @Provides
    @ViewModelScoped
    fun provideHomeRepository(
        remote: RemoteHomeDataSource,
        scope: CoroutineScope
    ): HomeRepository = OnlineFirstHomeRepository(remote, scope)
}