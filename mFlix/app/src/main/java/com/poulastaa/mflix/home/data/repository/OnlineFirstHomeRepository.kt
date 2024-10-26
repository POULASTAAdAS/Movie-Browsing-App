package com.poulastaa.mflix.home.data.repository

import com.poulastaa.mflix.core.domain.model.PrevPopular
import com.poulastaa.mflix.core.domain.repository.home.HomeRepository
import com.poulastaa.mflix.core.domain.repository.home.RemoteHomeDataSource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class OnlineFirstHomeRepository @Inject constructor(
    private val remote: RemoteHomeDataSource,
    private val scope: CoroutineScope
) : HomeRepository {
    override suspend fun getPopularData(): Result<List<PrevPopular>, DataError.Network> =
        remote.getPopularData()
}