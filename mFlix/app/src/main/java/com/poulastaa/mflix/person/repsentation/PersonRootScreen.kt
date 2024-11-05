package com.poulastaa.mflix.person.repsentation

import android.widget.Toast
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize
import com.poulastaa.mflix.core.presentation.designsystem.utils.ObserveAsEvent

@Composable
fun PersonRootScreen(
    viewModel: PersonViewModel,
    windowSizeClass: WindowSizeClass,
    navigateToDetails: (Long, PrevItemType) -> Unit,
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.uiEvent) { action ->
        when (action) {
            is PersonUiEvent.NavigateToDetails -> navigateToDetails(action.id, action.type)

            is PersonUiEvent.EmitToast -> Toast.makeText(
                context,
                action.message.asString(context),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            PersonCompactScreen(
                state = state,
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            )
        },
        mediumContent = {
            PersonMediumScreen(
                state = state,
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            )
        },
        expandedContent = {
            PersonExtendedScreen(
                state = state,
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            )
        }
    )
}