package com.poulastaa.mflix.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor() : ViewModel() {
    private var _state = MutableStateFlow(PersonUiState())
    val state = _state
        .onStart { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PersonUiState()
        )

    private var _uiState = Channel<PersonUiEvent>()
    val uiState = _uiState.receiveAsFlow()

    fun onAction(action: PersonUiAction) {
        when (action) {
            is PersonUiAction.OnItemClick -> viewModelScope.launch {
                _uiState.send(PersonUiEvent.NavigateToDetails(action.id, action.type))
            }
        }
    }

    fun loadData(personId:Long) {
        viewModelScope.launch {

        }
    }
}