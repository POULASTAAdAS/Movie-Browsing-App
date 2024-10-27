package com.poulastaa.mflix.core.domain.repository.home

import androidx.paging.PagingData
import com.poulastaa.mflix.core.domain.model.HomeDataType
import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.model.PrevPopular
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface RemoteHomeDataSource {
    suspend fun getPopularData(type: HomeDataType): Result<List<PrevPopular>, DataError.Network>
    suspend fun getTopRatedData(type: HomeDataType): Result<List<PrevItem>, DataError.Network>
    fun getPagingMore(type: HomeDataType): Flow<PagingData<PrevItem>>
}