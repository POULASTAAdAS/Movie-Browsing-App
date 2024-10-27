package com.poulastaa.mflix.home.data.repository

import androidx.paging.PagingData
import com.poulastaa.mflix.core.domain.model.HomeDataType
import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.model.PrevPopular
import com.poulastaa.mflix.core.domain.repository.home.HomeRepository
import com.poulastaa.mflix.core.domain.repository.home.RemoteHomeDataSource
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnlineFirstHomeRepository @Inject constructor(
    private val remote: RemoteHomeDataSource,
    private val scope: CoroutineScope
) : HomeRepository {
    override suspend fun getPopularData(type: HomeDataType): Result<List<PrevPopular>, DataError.Network> =
        remote.getPopularData(type)  // todo add isItemInFavorite for spotlight item

    override suspend fun getTopRatedData(type: HomeDataType): Result<List<PrevItem>, DataError.Network> =
        remote.getTopRatedData(type)

    override fun getPagingMore(type: HomeDataType): Flow<PagingData<PrevItem>> =
        remote.getPagingMore(type)
}