package com.poulastaa.mflix.details.data.repository

import androidx.paging.PagingData
import com.poulastaa.mflix.core.domain.model.MovieCollection
import com.poulastaa.mflix.core.domain.model.MovieCredits
import com.poulastaa.mflix.core.domain.model.MovieDetails
import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.repository.details.DetailsRepository
import com.poulastaa.mflix.core.domain.repository.details.RemoteDetailsDataSource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnlineFirstDetailsRepository @Inject constructor(
    private val remote: RemoteDetailsDataSource,
    private val scope: CoroutineScope,
) : DetailsRepository {
    override suspend fun getMovie(id: Long): Result<MovieDetails, DataError.Network> =
        remote.getMovie(id)

    override suspend fun getTvShow(id: Long) = remote.getTvShow(id)
    override suspend fun getMovieCollection(id: Long): Result<MovieCollection, DataError.Network> =
        remote.getMovieCollection(id)

    override suspend fun getMovieCastAndCrew(id: Long): Result<MovieCredits, DataError.Network> =
        remote.getMovieCastAndCrew(id)

    override suspend fun getRecommendation(list: String): Flow<PagingData<PrevItem>> =
        remote.getRecommendation(list)
}