package com.poulastaa.mflix.details.presentation

data class DetailsUiState(
    val isLoading: Boolean = false,
    val movie: UiMovieDetails = UiMovieDetails(),
    val cast: List<UiCrew> = listOf(
        UiCrew(
            id = 2524,
            name = "Chiwetel Ejiofor",
            profilePath = "https://image.tmdb.org/t/p/w500/mHSmt9qu2JzEPqnVWCGViv9Stnn.jpg",
            character = "Eddie Brock / Venom",
            order = 0
        ),
        UiCrew(
            id = 2524,
            name = "Tom Hardy",
            profilePath = "https://image.tmdb.org/t/p/w500/kq5DDnqqofoRI0t6ddtRlsJnNPT.jpg",
            character = "General Rex Strickland",
            order = 1
        ),
        UiCrew(
            id = 36594,
            name = "Juno Temple",
            profilePath = "https://image.tmdb.org/t/p/w500/wMpZcKp7zaHnmNQooqbve33577Q.jpg",
            character = "Dr. Teddy Paine",
            order = 2
        ),
        UiCrew(
            id = 1861573,
            name = "Clark Backo",
            profilePath = "https://image.tmdb.org/t/p/w500/d24KKFxfoql6PBsBPsejFgzhSlH.jpg",
            character = "Sadie Christmas",
            order = 3
        ),
        UiCrew(
            id = 7026,
            name = "Rhys Ifans",
            profilePath = "https://image.tmdb.org/t/p/w500/1D670EEsbky3EtO7XLG32A09p92.jpg",
            character = "Martin Moon",
            order = 4
        ),
        UiCrew(
            id = 1115,
            name = "Stephen Graham",
            profilePath = "https://image.tmdb.org/t/p/w500/hRq4Rq8IbLL4nGCu11N5ePESdI6.jpg",
            character = "Detective Mulligan",
            order = 5
        ),
        UiCrew(
            id = 2141479,
            name = "Peggy Lu",
            profilePath = "https://image.tmdb.org/t/p/w500/ng5eaDcOf9kSwIYGNmwF9wEfIHp.jpg",
            character = "Mrs. Chen",
            order = 6
        ),
        UiCrew(
            id = 10402,
            name = "Alanna Ubach",
            profilePath = "https://image.tmdb.org/t/p/w500/ffyBAEoW3bDgVJQV3GaHsZ9x29W.jpg",
            character = "Nova Moon",
            order = 7
        )
    ),
) {
    val isDataLoaded: Boolean = movie.title.isNotEmpty()
}

//data class UiMovieDetails(
//    val id: Long = -1,
//    val title: String = "",
//    val overview: String = "",
//    val posterPath: String = "",
//    val budget: Long = -1,
//    val voteAverage: Double = 0.0,
//    val releaseDate: String = "",
//    val runtime: Int = -1,
//    val status: String = "",
//    val homepage: String = "",
//    val productionCompany: List<UiMovieProductionCompany> = emptyList(),
//)

data class UiMovieDetails(
    val id: Long = 912649,
    val title: String = "Venom: The Last Dance",
    val overview: String = "Eddie and Venom are on the run. Hunted by both of their worlds and with the net closing in, the duo are forced into a devastating decision that will bring the curtains down on Venom and Eddie's last dance.",
    val posterPath: String = "https://image.tmdb.org/t/p/w500/k42Owka8v91trK1qMYwCQCNwJKr.jpg",
    val budget: Long = 120000000,
    val voteAverage: Double = 6.6,
    val releaseDate: String = "2024-10-22",
    val runtime: Int = -1,
    val status: String = "Released",
    val homepage: String = "https://venom.movie",
    val productionCompany: List<UiMovieProductionCompany> = listOf(
        UiMovieProductionCompany(
            id = 5,
            logoPath = "https://image.tmdb.org/t/p/w500/71BqEFAF4V3qjjMPCpLuyJFB9A/png",
            name = "Columbia Pictures",
        ),
        UiMovieProductionCompany(
            id = 84041,
            logoPath = "https://image.tmdb.org/t/p/w500/nw4kyc29QRpNtFbdsBHkRSFavvt/png",
            name = "Pascal Pictures",
        ),
        UiMovieProductionCompany(
            id = 53462,
            logoPath = "https://image.tmdb.org/t/p/w500/nx8B3Phlcse02w86RW4CJqzCnfL/png",
            name = "Matt Tolmach Productions",
        )
    ),
)

data class UiMovieProductionCompany(
    val id: Long = -1,
    val logoPath: String = "",
    val name: String = "",
)

data class UiCrew(
    val id: Long = -1,
    val name: String = "",
    val profilePath: String = "",
    val character: String = "",
    val order: Int = -1,
)