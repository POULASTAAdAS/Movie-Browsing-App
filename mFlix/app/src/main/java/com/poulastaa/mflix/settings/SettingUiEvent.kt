package com.poulastaa.mflix.settings

sealed interface SettingUiEvent {
    data object OnLogout : SettingUiEvent
}