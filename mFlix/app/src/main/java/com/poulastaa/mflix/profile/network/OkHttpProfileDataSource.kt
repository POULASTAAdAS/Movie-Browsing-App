package com.poulastaa.mflix.profile.network

import com.poulastaa.mflix.core.data.network.get
import com.poulastaa.mflix.core.domain.model.EndPoints
import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.repository.profile.RemoteProfileDatasource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.core.domain.utils.map
import com.poulastaa.mflix.home.data.mapper.toPrevItem
import com.poulastaa.mflix.home.network.model.PrevMovieWrapper
import com.poulastaa.mflix.home.network.model.PrevTvWrapper
import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpProfileDataSource @Inject constructor(
    private val client: OkHttpClient,
) : RemoteProfileDatasource {
    override suspend fun getItemsInFavourite(idList: List<Long>): Result<List<PrevItem>, DataError.Network> {
        return Result.Error(DataError.Network.NOT_FOUND)
    }

    override suspend fun getUpcomingMovies(): Result<List<PrevItem>, DataError.Network> {
        val result = client.get<PrevMovieWrapper>(
            route = EndPoints.UpComingMovies.route
        )

        return result.map { list ->
            list.results.map { dto ->
                dto.toPrevItem()
            }
        }
    }

    override suspend fun getUpcomingTvShows(): Result<List<PrevItem>, DataError.Network> {
        val result = client.get<PrevTvWrapper>(
            route = EndPoints.UpComingTv.route
        )

        return result.map { list ->
            list.results.map { dto ->
                dto.toPrevItem()
            }
        }
    }
}