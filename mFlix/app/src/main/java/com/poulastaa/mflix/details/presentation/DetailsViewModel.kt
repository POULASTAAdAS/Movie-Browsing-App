package com.poulastaa.mflix.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.domain.repository.details.DetailsRepository
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.home.presentation.toPrevItemType
import com.poulastaa.mflix.home.presentation.toUiPrevItem
import com.poulastaa.mflix.home.presentation.toUiPrevItemType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repo: DetailsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(DetailsUiState())
    val state = _state
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(0),
            initialValue = DetailsUiState()
        )

    private val _recom: MutableStateFlow<PagingData<UiPrevItem>> =
        MutableStateFlow(PagingData.empty())
    val recom = _recom
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    private var loadRecomJob: Job? = null

    private val _uiState = Channel<DetailsUiEvent>()
    val uiState = _uiState.receiveAsFlow()

    fun loadDetails(
        id: Long,
        type: PrevItemType,
    ) {
        _state.update {
            it.copy(
                type = type.toUiPrevItemType()
            )
        }

        viewModelScope.launch {
            when (type) {
                PrevItemType.MOVIE -> {
                    when (val result = repo.getMovie(id)) {
                        is Result.Error -> {

                        }

                        is Result.Success -> {
                            val data = result.data

                            _state.update {
                                it.copy(
                                    movie = data.toUiMovieDetails()
                                )
                            }

                            loadMovieCredits(data.id)
                            if (data.belongs_to_collection.id != -1L)
                                loadMovieCollection(data.belongs_to_collection.id)

                            if (_state.value.movie.genre.isNotEmpty()) {
                                loadRecomJob?.cancel()
                                loadRecomJob = loadRecommendation(
                                    idList = _state.value.movie.genre
                                        .map { it.id }
                                        .joinToString("|")
                                )
                            }
                        }
                    }
                }

                PrevItemType.TV_SERIES -> {

                }
            }
        }
    }

    fun onAction(action: DetailsUiAction) {
        when (action) {
            is DetailsUiAction.OnPersonClick -> {
                viewModelScope.launch {
                    _uiState.send(DetailsUiEvent.NavigateToPerson(action.id))
                }
            }

            DetailsUiAction.OnCastMemberDetailsClick -> {
                _state.update {
                    it.copy(
                        details = it.details.copy(
                            isDetailsVisible = true,
                            list = it.cast
                        )
                    )
                }
            }

            DetailsUiAction.OnCrewMemberDetailsClick -> {
                _state.update {
                    it.copy(
                        details = it.details.copy(
                            isDetailsVisible = true,
                            list = it.crew
                        )
                    )
                }
            }

            DetailsUiAction.OnDetailsHide -> {
                _state.update {
                    it.copy(
                        details = UiDetails()
                    )
                }
            }

            is DetailsUiAction.OnItemClick -> {
                viewModelScope.launch {
                    _uiState.send(
                        DetailsUiEvent.NavigateToDetails(
                            action.id,
                            action.type.toPrevItemType()
                        )
                    )
                }
            }

        }
    }


    private fun loadMovieCredits(movieId: Long) {
        viewModelScope.launch {
            val credits = repo.getMovieCastAndCrew(movieId)

            if (credits is Result.Success) _state.update {
                it.copy(
                    cast = credits.data.cast.map { person -> person.toUiPerson() },
                    crew = credits.data.crew.map { person -> person.toUiPerson() }
                )
            }
        }
    }

    private fun loadMovieCollection(collectionId: Long) {
        viewModelScope.launch {
            val collection = repo.getMovieCollection(collectionId)

            if (collection is Result.Success) {
                val items = collection.data.parts.filterNot { it.id == _state.value.movie.id }
                    .sortedBy { it.release_date }

                _state.update {
                    it.copy(
                        collection = collection.data.toUiMovieCollection().copy(
                            items = items.map { item -> item.toUiCollectionItem() })
                    )
                }
            }
        }
    }

    private fun loadRecommendation(idList: String) = viewModelScope.launch {
        _recom.update {
            PagingData.empty()
        }

        repo.getRecommendation(idList)
            .cachedIn(viewModelScope)
            .collectLatest { pagingData ->
                _recom.update {
                    pagingData
                        .filter { it.id != _state.value.movie.id }
                        .map { item ->
                            item.toUiPrevItem()
                        }
                }
            }
    }
}