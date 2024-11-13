package com.poulastaa.mflix.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poulastaa.mflix.core.presentation.designsystem.utils.ObserveAsEvent

@Composable
fun SettingsRootScreen(
    viewmodel: SettingsViewmodel,
    navigateBack: () -> Unit,
    logOut: () -> Unit,
) {
    ObserveAsEvent(viewmodel.event) {
        when (it) {
            SettingUiEvent.OnLogout -> logOut()
        }
    }

    val state = viewmodel.state.collectAsStateWithLifecycle()

    SettingCompactScreen(
        state = state.value,
        onAction = viewmodel::onAction,
        navigateBack = navigateBack
    )
}