package com.poulastaa.mflix.core.domain.repository.home

import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.model.PrevPopular
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result

interface RemoteHomeDataSource {
    suspend fun getPopularData(): Result<List<PrevPopular>, DataError.Network>
    suspend fun getTopRatedData(): Result<List<PrevItem>, DataError.Network>
}