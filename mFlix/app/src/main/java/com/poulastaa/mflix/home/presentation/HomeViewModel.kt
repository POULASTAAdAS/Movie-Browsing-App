package com.poulastaa.mflix.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.domain.model.UiUser
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.domain.repository.home.HomeRepository
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.core.presentation.designsystem.repository.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ds: DataStoreRepository,
    private val repo: HomeRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state
        .onStart {
            loadUser()
            loadPopularData()
            loadTopRatedData()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(6_00_000),
            initialValue = HomeUiState(),
        )

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _more: MutableStateFlow<PagingData<UiPrevItem>> =
        MutableStateFlow(PagingData.empty())
    val more = _more
        .onStart {
            loadMoreJob?.cancel()
            loadMoreJob = loadMore()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    private var loadMoreJob: Job? = null

    fun onAction(action: HomeUiAction) {
        when (action) {
            HomeUiAction.OnSearchClick -> {
                viewModelScope.launch {
                    _uiEvent.send(HomeUiEvent.NavigateToSearch)
                }
            }


            HomeUiAction.OnSpotlightFavouriteClick -> {

            }

            is HomeUiAction.OnFilterTypeChange -> {
                if (_state.value.filterType == action.type) return

                _state.update {
                    it.copy(
                        filterType = action.type
                    )
                }

                _state.update {
                    it.copy(
                        popularList = emptyList(),
                        topRated = emptyList(),
                    )
                }

                loadPopularData()
                loadTopRatedData()
                loadMoreJob?.cancel()
                loadMoreJob = loadMore()
            }

            is HomeUiAction.OnItemClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        HomeUiEvent.NavigateToDetails(
                            id = action.id,
                            type = action.type.toPrevItemType()
                        )
                    )
                }
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            val user = ds.readUser()

            _state.update {
                it.copy(
                    user = UiUser(
                        name = user.name,
                        email = user.email,
                        coverImage = user.profilePic,
                    )
                )
            }
        }
    }

    private fun loadPopularData() {
        viewModelScope.launch {
            when (val result = repo.getPopularData(_state.value.filterType.toHomeDataType())) {
                is Result.Error -> {
                    when (result.error) {
                        DataError.Network.NO_INTERNET -> _uiEvent.send(
                            HomeUiEvent.EmitToast(
                                UiText.StringResource(
                                    R.string.error_no_internet
                                )
                            )
                        )

                        else -> _uiEvent.send(
                            HomeUiEvent.EmitToast(
                                UiText.StringResource(
                                    R.string.error_something_went_wrong
                                )
                            )
                        )
                    }
                }

                is Result.Success -> {
                    val data = result.data.map { item ->
                        UiPrevItem(
                            id = item.id,
                            title = item.title,
                            description = item.overview,
                            rating = item.rating,
                            imageUrl = item.coverImage,
                            type = item.type.toUiPrevItemType(),
                            isInFavourite = false
                        )
                    }
                    val spotlights = data.random(Random(System.currentTimeMillis()))

                    _state.update {
                        it.copy(
                            spotLight = spotlights,
                            popularList = data.filterNot { item -> item.id == spotlights.id }
                        )
                    }
                }
            }
        }
    }

    private fun loadTopRatedData() {
        viewModelScope.launch {
            when (val result = repo.getTopRatedData(_state.value.filterType.toHomeDataType())) {
                is Result.Error -> when (result.error) {
                    DataError.Network.NO_INTERNET -> _uiEvent.send(
                        HomeUiEvent.EmitToast(
                            UiText.StringResource(
                                R.string.error_no_internet
                            )
                        )
                    )

                    else -> _uiEvent.send(
                        HomeUiEvent.EmitToast(
                            UiText.StringResource(
                                R.string.error_something_went_wrong
                            )
                        )
                    )
                }

                is Result.Success -> _state.update {
                    it.copy(
                        topRated = result.data.map { item ->
                            UiPrevItem(
                                id = item.id,
                                title = item.title,
                                description = item.description,
                                rating = item.rating,
                                imageUrl = item.coverImage,
                                type = item.type.toUiPrevItemType(),
                                isInFavourite = false
                            )
                        }
                    )
                }
            }
        }
    }

    private fun loadMore() = viewModelScope.launch {
        _more.update {
            PagingData.empty()
        }

        repo.getPagingMore(_state.value.filterType.toHomeDataType())
            .cachedIn(viewModelScope)
            .collectLatest { pagingData ->
                _more.update {
                    pagingData.map { item ->
                        item.toUiPrevItem()
                    }
                }
            }
    }
}