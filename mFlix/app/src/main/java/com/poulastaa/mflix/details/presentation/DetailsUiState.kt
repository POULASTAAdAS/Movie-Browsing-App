package com.poulastaa.mflix.details.presentation

import androidx.compose.runtime.Stable
import com.poulastaa.mflix.core.domain.model.UiPrevItemType

@Stable
data class DetailsUiState(
    val type: UiPrevItemType = UiPrevItemType.MOVIE,

    val movie: UiMovieDetails = UiMovieDetails(),
    val collection: UiMovieCollection = UiMovieCollection(),
    val cast: List<UiPerson> = emptyList(),
    val crew: List<UiPerson> = emptyList(),

    val details: UiDetails = UiDetails(),
) {
    val isDataLoaded: Boolean = movie.title.isNotEmpty() && cast.isNotEmpty() && crew.isNotEmpty()
}

@Stable
data class UiMovieDetails(
    val id: Long = -1,
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val budget: Long = -1,
    val voteAverage: Float = 0f,
    val releaseDate: String = "",
    val runtime: Int = -1,
    val status: String = "",
    val homepage: String = "",
    val productionCompany: List<UiMovieProductionCompany> = emptyList(),
    val genre: List<UiGenre> = emptyList(),
)

data class UiMovieProductionCompany(
    val id: Long = -1,
    val logoPath: String = "",
    val name: String = "",
)

data class UiPerson(
    val id: Long = -1,
    val name: String = "",
    val profilePath: String = "",
    val character: String = "",
    val order: Int = -1,
)

@Stable
data class UiMovieCollection(
    val id: Long = -1,
    val name: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val items: List<UiCollectionItem> = emptyList(),
)

data class UiCollectionItem(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val mediaType: String,
    val releaseDate: String,
    val voteAverage: Double,
)

data class UiGenre(
    val id: Long,
    val name: String,
)

data class UiDetails(
    val isDetailsVisible: Boolean = false,
    val list: List<UiPerson> = emptyList(),
)