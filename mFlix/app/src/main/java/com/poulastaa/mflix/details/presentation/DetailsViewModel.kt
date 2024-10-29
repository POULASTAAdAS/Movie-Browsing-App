package com.poulastaa.mflix.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.domain.repository.details.DetailsRepository
import com.poulastaa.mflix.core.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
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
        .onStart { }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailsUiState()
        )


    fun loadDetails(
        id: Long,
        type: PrevItemType,
    ) {
        viewModelScope.launch {
            when (val result = repo.getMovie(id)) {
                is Result.Error -> {

                }

                is Result.Success -> {
                    // to do get other data

                    _state.update {
                        it.copy(
                            movie = result.data.toUiMovieDetails()
                        )
                    }
                }
            }
        }
    }
}