package com.poulastaa.mflix.person.repsentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.repository.person.PersonRepository
import com.poulastaa.mflix.core.domain.utils.DataError
import com.poulastaa.mflix.core.domain.utils.Result
import com.poulastaa.mflix.core.presentation.designsystem.repository.UiText
import com.poulastaa.mflix.home.presentation.toPrevItemType
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
class PersonViewModel @Inject constructor(
    private val repo: PersonRepository,
) : ViewModel() {
    private var _state = MutableStateFlow(PersonUiState())
    val state = _state
        .onStart { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PersonUiState()
        )

    private var _uiEvent = Channel<PersonUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: PersonUiAction) {
        when (action) {
            is PersonUiAction.OnItemClick -> viewModelScope.launch {
                _uiEvent.send(PersonUiEvent.NavigateToDetails(action.id, action.type.toPrevItemType()))
            }
        }
    }

    fun loadData(personId: Long) {
        viewModelScope.launch {
            when (val result = repo.getData(personId)) {
                is Result.Error -> {
                    when (result.error) {
                        DataError.Network.NO_INTERNET -> _uiEvent.send(
                            PersonUiEvent.EmitToast(
                                UiText.StringResource(R.string.error_no_internet)
                            )
                        )

                        else -> _uiEvent.send(
                            PersonUiEvent.EmitToast(
                                UiText.StringResource(
                                    R.string.error_something_went_wrong
                                )
                            )
                        )
                    }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            person = result.data.details.toUiPerson(),
                            knownFor = result.data.knownFor
                                .map { item -> item.toUiPrevItem() }
                        )
                    }
                }
            }
        }
    }
}