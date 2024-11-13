package com.poulastaa.mflix.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.domain.model.UiPrevItemType
import com.poulastaa.mflix.core.domain.model.UiUser
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.domain.repository.profile.ProfileRepository
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.home.presentation.toPrevItemType
import com.poulastaa.mflix.profile.presentation.ProfileUiEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(
    private val ds: DataStoreRepository,
    private val repo: ProfileRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state
        .onStart {
            readUser()
            loadMoreUpcomingMovies()
            loadMoreUpcomingTvShows()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ProfileUiState()
        )

    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: ProfileUiAction) {
        when (action) {
            is ProfileUiAction.OnItemClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        OnItemClick(
                            action.id,
                            action.type.toPrevItemType()
                        )
                    )
                }
            }

            ProfileUiAction.OnEditClick -> {

            }

            ProfileUiAction.OnMoreUpcomingMovieClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        OnUpComingTypeClick(PrevItemType.MOVIE)
                    )
                }
            }

            ProfileUiAction.OnMoreUpcomingTvClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        OnUpComingTypeClick(PrevItemType.TV_SERIES)
                    )
                }
            }

            ProfileUiAction.OnSettingClick -> {
                viewModelScope.launch {
                    _uiEvent.send(ProfileUiEvent.NavigateToSetting)
                }
            }
        }
    }

    private fun readUser() {
        viewModelScope.launch {
            val user = ds.readUser()

            _state.update {
                it.copy(
                    user = UiUser(
                        name = user.name,
                        coverImage = user.profilePic,
                        email = user.email
                    )
                )
            }
        }
    }

    private fun loadMoreUpcomingMovies() {
        viewModelScope.launch {
            when (val result = repo.getUpcomingMovies()) {
                is Result.Error -> {

                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            upcomingMovie = result.data.map { item ->
                                UiPrevItem(
                                    id = item.id,
                                    title = item.title,
                                    description = item.description,
                                    rating = item.rating,
                                    isInFavourite = item.isInFavourite,
                                    imageUrl = item.coverImage,
                                    type = UiPrevItemType.MOVIE
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    private fun loadMoreUpcomingTvShows() {
        viewModelScope.launch {
            when (val result = repo.getUpcomingTvShows()) {
                is Result.Error -> {

                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            upcomingTv = result.data.map { item ->
                                UiPrevItem(
                                    id = item.id,
                                    title = item.title,
                                    description = item.description,
                                    rating = item.rating,
                                    isInFavourite = item.isInFavourite,
                                    imageUrl = item.coverImage,
                                    type = UiPrevItemType.MOVIE
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}