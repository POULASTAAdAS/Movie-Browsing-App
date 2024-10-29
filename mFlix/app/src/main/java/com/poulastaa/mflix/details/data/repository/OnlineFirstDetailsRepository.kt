package com.poulastaa.mflix.details.data.repository

import com.poulastaa.mflix.core.domain.model.MovieDetails
import com.poulastaa.mflix.core.domain.repository.details.DetailsRepository
import com.poulastaa.mflix.core.domain.repository.details.RemoteDetailsDataSource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class OnlineFirstDetailsRepository @Inject constructor(
    private val remote: RemoteDetailsDataSource,
    private val scope: CoroutineScope,
) : DetailsRepository {
    override suspend fun getMovie(id: Long): Result<MovieDetails, DataError.Network> =
        remote.getMovie(id)

    override suspend fun getTvShow(id: Long) = remote.getTvShow(id)
}