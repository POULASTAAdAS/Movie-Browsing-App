package com.poulastaa.mflix.core.domain.repository.person

import com.poulastaa.mflix.core.domain.model.PersonPayload
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result

interface PersonRepository {
    suspend fun getData(personId: Long): Result<PersonPayload, DataError.Network>
}