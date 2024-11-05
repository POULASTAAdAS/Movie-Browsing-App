package com.poulastaa.mflix.core.domain.model

data class PersonPayload(
    val details: PersonDetails = PersonDetails(),
    val knownFor: List<KnownForItem> = emptyList(),
)
