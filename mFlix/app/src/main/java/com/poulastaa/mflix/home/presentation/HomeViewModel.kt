package com.poulastaa.mflix.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.domain.model.UiPrevItemType
import com.poulastaa.mflix.core.domain.model.UiUser
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.domain.repository.home.HomeRepository
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ds: DataStoreRepository,
    private val repo: HomeRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state
        .onStart {
            loadUser()
            loadPopularData()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(),
        )

    fun onAction(action: HomeUiAction) {
        when (action) {
            HomeUiAction.OnSearchClick -> {

            }

            is HomeUiAction.OnItemClick -> {

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
            when (val result = repo.getPopularData()) {
                is Result.Error -> {
                    when (result.error) {
                        DataError.Network.NO_INTERNET -> {

                        }

                        else -> {

                        }
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

    private fun PrevItemType.toUiPrevItemType() = when (this) {
        PrevItemType.MOVIE -> UiPrevItemType.MOVIE
        PrevItemType.TV_SERIES -> UiPrevItemType.TV_SHOW
    }
}