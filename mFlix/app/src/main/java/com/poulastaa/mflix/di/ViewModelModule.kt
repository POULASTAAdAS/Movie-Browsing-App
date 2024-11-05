package com.poulastaa.mflix.di

import com.poulastaa.mflix.auth.data.repository.AuthRepositoryImpl
import com.poulastaa.mflix.auth.network.repository.OkHttpAuthDataSource
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.domain.repository.auth.AuthRepository
import com.poulastaa.mflix.core.domain.repository.auth.RemoteAuthDataSource
import com.poulastaa.mflix.core.domain.repository.details.DetailsRepository
import com.poulastaa.mflix.core.domain.repository.details.RemoteDetailsDataSource
import com.poulastaa.mflix.core.domain.repository.home.HomeRepository
import com.poulastaa.mflix.core.domain.repository.home.RemoteHomeDataSource
import com.poulastaa.mflix.core.domain.repository.person.PersonRepository
import com.poulastaa.mflix.core.domain.repository.person.RemotePersonDataSource
import com.poulastaa.mflix.core.domain.repository.profile.ProfileRepository
import com.poulastaa.mflix.core.domain.repository.profile.RemoteProfileDatasource
import com.poulastaa.mflix.details.data.repository.OnlineFirstDetailsRepository
import com.poulastaa.mflix.details.network.DetailsRecomPagerSource
import com.poulastaa.mflix.details.network.OkHttpDetailsDataSource
import com.poulastaa.mflix.home.data.repository.OnlineFirstHomeRepository
import com.poulastaa.mflix.home.network.HomeMorePagerSource
import com.poulastaa.mflix.home.network.OkHttpRemoteHomeDataSource
import com.poulastaa.mflix.person.data.repository.OnlineFirstPersonRepository
import com.poulastaa.mflix.person.network.OkHttpPersonDataSource
import com.poulastaa.mflix.profile.data.OfflineFirstProfileRepository
import com.poulastaa.mflix.profile.network.OkHttpProfileDataSource
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
        client: OkHttpClient,
    ): RemoteAuthDataSource = OkHttpAuthDataSource(client)

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        cookieManager: CookieManager,
        ds: DataStoreRepository,
        scope: CoroutineScope,
        remote: RemoteAuthDataSource,
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
        ds: DataStoreRepository,
    ): HomeMorePagerSource = HomeMorePagerSource(client, ds)

    @Provides
    @ViewModelScoped
    fun provideHomeRemoteDataSource(
        client: OkHttpClient,
        pager: HomeMorePagerSource,
    ): RemoteHomeDataSource = OkHttpRemoteHomeDataSource(client, pager)

    @Provides
    @ViewModelScoped
    fun provideHomeRepository(
        remote: RemoteHomeDataSource,
        scope: CoroutineScope,
    ): HomeRepository = OnlineFirstHomeRepository(remote, scope)

    @Provides
    @ViewModelScoped
    fun provideProfileRemoteDataSource(
        client: OkHttpClient,
    ): RemoteProfileDatasource = OkHttpProfileDataSource(client)

    @Provides
    @ViewModelScoped
    fun provideProfileRepository(
        remote: RemoteProfileDatasource,
        scope: CoroutineScope,
    ): ProfileRepository = OfflineFirstProfileRepository(remote, scope)

    @Provides
    @ViewModelScoped
    fun provideDetailsRecommendationPagerSource(
        client: OkHttpClient,
        ds: DataStoreRepository,
    ): DetailsRecomPagerSource = DetailsRecomPagerSource(client, ds)

    @Provides
    @ViewModelScoped
    fun provideDetailsRemoteDataSource(
        client: OkHttpClient,
        pager: DetailsRecomPagerSource,
    ): RemoteDetailsDataSource = OkHttpDetailsDataSource(client, pager)

    @Provides
    @ViewModelScoped
    fun provideDetailsRepository(
        remote: RemoteDetailsDataSource,
        scope: CoroutineScope,
    ): DetailsRepository = OnlineFirstDetailsRepository(remote, scope)

    @Provides
    @ViewModelScoped
    fun providePersonRemoteDataSource(
        client: OkHttpClient,
    ): RemotePersonDataSource = OkHttpPersonDataSource(client)

    @Provides
    @ViewModelScoped
    fun providePersonRepository(
        remote: RemotePersonDataSource,
        scope: CoroutineScope,
    ): PersonRepository = OnlineFirstPersonRepository(remote, scope)
}