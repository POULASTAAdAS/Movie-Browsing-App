package com.poulastaa.mflix.core.domain.repository.profile

import com.poulastaa.mflix.core.domain.model.PrevItem
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result

interface ProfileRepository {
    suspend fun getItemsInFavourite(): Result<List<PrevItem>, DataError.Network>

    suspend fun getUpcomingMovies(): Result<List<PrevItem>, DataError.Network>
    suspend fun getUpcomingTvShows(): Result<List<PrevItem>, DataError.Network>
}