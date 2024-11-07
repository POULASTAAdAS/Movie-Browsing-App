package com.poulastaa.mflix.search.presentation

import android.widget.Toast
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.utils.ObserveAsEvent

@Composable
fun SearchRootScreen(
    viewModel: SearchViewModel,
    navigateToDetails: (Long, PrevItemType) -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val data = viewModel.data.collectAsLazyPagingItems()

    ObserveAsEvent(viewModel.uiEvent) { event ->
        when (event) {
            is SearchUiEvent.EmitToast -> Toast.makeText(
                context,
                event.message.asString(context),
                Toast.LENGTH_LONG
            ).show()

            is SearchUiEvent.NavigateToDetails -> navigateToDetails(event.id, event.type)
        }
    }

    SearchScreen(
        state = state,
        data = data,
        onAction = viewModel::onAction,
    )
}