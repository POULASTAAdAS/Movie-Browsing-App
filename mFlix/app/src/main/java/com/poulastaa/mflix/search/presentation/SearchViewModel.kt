package com.poulastaa.mflix.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.poulastaa.mflix.core.domain.repository.search.SearchRepository
import com.poulastaa.mflix.core.navigation.AppScreen
import com.poulastaa.mflix.home.presentation.toPrevItemType
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

private const val LIVE_TIME = 6_00_000L

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: SearchRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchUiState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(LIVE_TIME),
            initialValue = SearchUiState()
        )

    private val _data: MutableStateFlow<PagingData<UiSearchQueryItem>> =
        MutableStateFlow(PagingData.empty())
    val data = _data
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(LIVE_TIME),
            initialValue = PagingData.empty()
        )

    private val _uiEvent = Channel<SearchUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var searchJob: Job? = null

    fun updateSearchType(
        type: AppScreen.SearchType
    ) {
        _state.update {
            it.copy(
                searchType = type
            )
        }
    }

    fun onAction(action: SearchUiAction) {
        when (action) {
            SearchUiAction.OnClear -> {
                _state.update {
                    it.copy(
                        query = ""
                    )
                }

                searchJob?.cancel()
                searchJob = search()
            }

            SearchUiAction.OnItemViewTypeToggle -> {
                _state.update {
                    it.copy(
                        viewType = when (it.viewType) {
                            UiSearchItemViewType.GRID -> UiSearchItemViewType.LIST
                            UiSearchItemViewType.LIST -> UiSearchItemViewType.GRID
                        }
                    )
                }
            }

            is SearchUiAction.OnSearchTypeToggle -> {
                if (_state.value.searchType == action.type) return

                _state.update {
                    it.copy(
                        searchType = action.type
                    )
                }

                searchJob?.cancel()
                searchJob = search()
            }

            is SearchUiAction.OnSearchQueryChange -> {
                val oldQuery = _state.value.query

                _state.update {
                    it.copy(
                        query = action.query.trim()
                    )
                }

                if (oldQuery == _state.value.query) return
                searchJob?.cancel()
                searchJob = search()
            }

            is SearchUiAction.OnItemClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        SearchUiEvent.NavigateToDetails(
                            action.id,
                            action.type.toPrevItemType()
                        )
                    )
                }
            }
        }
    }

    private fun search() = viewModelScope.launch {
        _data.update {
            PagingData.empty()
        }

        repo.searchResult(
            type = _state.value.searchType.toHomeDataType(),
            query = _state.value.query.trim(),
        ).cachedIn(viewModelScope).collectLatest { list ->
            _data.update {
                list.map { it.toUiSearchQueryItem() }
            }
        }
    }
}