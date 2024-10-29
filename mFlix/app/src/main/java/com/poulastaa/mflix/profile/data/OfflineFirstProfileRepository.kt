package com.poulastaa.mflix.profile.data

import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.repository.profile.ProfileRepository
import com.poulastaa.mflix.core.domain.repository.profile.RemoteProfileDatasource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class OfflineFirstProfileRepository @Inject constructor(
    private val remote: RemoteProfileDatasource,
    private val scope: CoroutineScope,
) : ProfileRepository {
    override suspend fun getItemsInFavourite(): Result<List<PrevItem>, DataError.Network> =
        remote.getItemsInFavourite(emptyList()) // todo

    override suspend fun getUpcomingMovies(): Result<List<PrevItem>, DataError.Network> =
        remote.getUpcomingMovies()

    override suspend fun getUpcomingTvShows(): Result<List<PrevItem>, DataError.Network> =
        remote.getUpcomingTvShows()
}