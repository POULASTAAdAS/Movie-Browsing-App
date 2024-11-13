package com.poulastaa.mflix.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val LIVE_TIME = 0L

@HiltViewModel
class SettingsViewmodel @Inject constructor(
    private val ds: DataStoreRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SettingUiState())
    val state = _state
        .onStart { loadAdultState() }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(LIVE_TIME),
            initialValue = SettingUiState(),
        )

    private val _uiEvent = Channel<SettingUiEvent>()
    val event = _uiEvent.receiveAsFlow()

    fun onAction(action: SettingUiAction) {
        when (action) {
            SettingUiAction.OnAdultTypeToggle -> {
                viewModelScope.launch {
                    ds.updateAdult(!_state.value.toggleState)
                }

                _state.update {
                    it.copy(
                        toggleState = !it.toggleState
                    )
                }
            }

            SettingUiAction.LogOut -> {
                viewModelScope.launch {
                    ds.storeSignInState(Screen.Intro)
                    _uiEvent.send(SettingUiEvent.OnLogout)
                }
            }
        }
    }

    private fun loadAdultState() {
        viewModelScope.launch {
            val state = ds.readAdult().first()

            _state.update {
                it.copy(
                    toggleState = state
                )
            }
        }
    }
}