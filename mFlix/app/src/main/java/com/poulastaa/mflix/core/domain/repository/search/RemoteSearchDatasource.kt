package com.poulastaa.mflix.core.domain.repository.search

import androidx.paging.PagingData
import com.poulastaa.mflix.core.domain.model.HomeDataType
import com.poulastaa.mflix.core.domain.model.SearchPayload
import kotlinx.coroutines.flow.Flow

interface RemoteSearchDatasource {
    fun searchResult(
        type: HomeDataType,
        query: String,
        isUpcoming: Boolean
    ): Flow<PagingData<SearchPayload>>
}