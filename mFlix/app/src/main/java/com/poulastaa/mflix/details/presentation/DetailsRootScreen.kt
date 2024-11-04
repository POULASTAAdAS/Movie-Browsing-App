package com.poulastaa.mflix.details.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.PrevItemType
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.designsystem.utils.AppScreenWindowSize
import com.poulastaa.mflix.core.presentation.designsystem.utils.ObserveAsEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsRootScreen(
    viewModel: DetailsViewModel,
    windowSizeClass: WindowSizeClass,
    navigateToDetails: (Long, PrevItemType) -> Unit,
    navigateToPerson: (Long) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val config = LocalConfiguration.current

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.details.isDetailsVisible) {
        if (!sheetState.isVisible && state.details.isDetailsVisible) sheetState.show()
    }


    ObserveAsEvent(viewModel.uiState) { event ->
        when (event) {
            is DetailsUiEvent.NavigateToDetails -> navigateToDetails(event.id, event.type)
            is DetailsUiEvent.NavigateToPerson -> navigateToPerson(event.id)
        }
    }

    AppScreenWindowSize(
        windowSizeClass = windowSizeClass,
        compactContent = {
            DetailsSmallScreen(
                state = state,
                recom = viewModel.recom.collectAsLazyPagingItems(),
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            )
        },
        mediumContent = {
            DetailsMediumScreen(
                state = state,
                recom = viewModel.recom.collectAsLazyPagingItems(),
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            )
        },
        expandedContent = {
            if (config.screenWidthDp > 980) DetailsExpandedScreen(
                state = state,
                recom = viewModel.recom.collectAsLazyPagingItems(),
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            ) else DetailsSmallExpandedScreen(
                state = state,
                recom = viewModel.recom.collectAsLazyPagingItems(),
                onAction = viewModel::onAction,
                navigateBack = navigateBack
            )
        }
    )

    if (sheetState.isVisible) ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                viewModel.onAction(DetailsUiAction.OnDetailsHide)
            }
        },
        shape = MaterialTheme.shapes.medium
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1),
            contentPadding = PaddingValues(MaterialTheme.dimens.medium1)
        ) {
            items(state.details.list) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.small)
                        .clickable {
                            viewModel.onAction(DetailsUiAction.OnPersonClick(item.id))
                        }
                ) {
                    Card(
                        modifier = Modifier
                            .width(140.dp)
                            .fillMaxHeight(),
                        shape = MaterialTheme.shapes.extraSmall,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 5.dp
                        )
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(item.profilePath)
                                .error(R.drawable.ic_user_filled)
                                .placeholder(R.drawable.ic_user_filled)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(Modifier.width(MaterialTheme.dimens.medium1))

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.name,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "Role: ${item.character}"
                        )
                    }
                }
            }
        }
    }
}