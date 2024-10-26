package com.poulastaa.mflix.home.network

import com.poulastaa.mflix.core.data.network.get
import com.poulastaa.mflix.core.domain.model.EndPoints
import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.model.PrevPopular
import com.poulastaa.mflix.core.domain.repository.home.RemoteHomeDataSource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.core.domain.utils.map
import com.poulastaa.mflix.home.data.mapper.toPrevPopular
import com.poulastaa.mflix.home.network.model.PopularWrapperDto
import okhttp3.OkHttpClient
import javax.inject.Inject

class OkHttpRemoteHomeDataSource @Inject constructor(
    private val client: OkHttpClient
) : RemoteHomeDataSource {
    override suspend fun getPopularData(): Result<List<PrevPopular>, DataError.Network> {
        val result = client.get<PopularWrapperDto>(
            route = EndPoints.Popular.route,
            params = emptyList()
        )

        return result.map { list ->
            list.results.map {
                it.toPrevPopular()
            }
        }
    }

    override suspend fun getTopRatedData(): Result<List<PrevItem>, DataError.Network> {
        return Result.Error(DataError.Network.NOT_FOUND)

    }
}