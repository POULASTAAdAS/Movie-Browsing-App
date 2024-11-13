package com.poulastaa.mflix.settings

sealed interface SettingUiAction {
    data object OnAdultTypeToggle : SettingUiAction
    data object LogOut : SettingUiAction
}