package com.poulastaa.mflix.core.domain.repository.search

import androidx.paging.PagingData
import com.poulastaa.mflix.core.domain.model.HomeDataType
import com.poulastaa.mflix.core.domain.model.SearchPayload
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchResult(
        type: HomeDataType,
        query: String,
    ): Flow<PagingData<SearchPayload>>
}