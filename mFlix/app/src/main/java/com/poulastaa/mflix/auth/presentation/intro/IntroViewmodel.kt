package com.poulastaa.mflix.auth.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewmodel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(IntroUiState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<IntroUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: IntroUiAction) {
        when (action) {
            IntroUiAction.OnEmailClick -> {
                viewModelScope.launch {
                    _uiEvent.send(IntroUiEvent.NavigateToEmailLogIn)
                }
            }

            IntroUiAction.OnGoogleClick -> {
                if (_state.value.isMakingApiCall) return

                viewModelScope.launch {
                    _uiEvent.send(IntroUiEvent.StartGoogleAuth)
                    _state.update {
                        it.copy(
                            isMakingApiCall = true
                        )
                    }
                }
            }
        }
    }
}