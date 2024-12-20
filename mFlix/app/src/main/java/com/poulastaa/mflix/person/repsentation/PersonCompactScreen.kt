package com.poulastaa.mflix.person.repsentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.core.presentation.designsystem.theme.ArrowUpIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.FilledUserIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.AppBackButton
import com.poulastaa.mflix.details.presentation.components.DetailsLoadingScreen
import com.poulastaa.mflix.person.data.model.UiGenderType
import com.poulastaa.mflix.person.repsentation.components.detailsCommonContent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonCompactScreen(
    state: PersonUiState,
    onAction: (PersonUiAction) -> Unit,
    navigateBack: () -> Unit,
) {
    val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val config = LocalConfiguration.current
    val cardHeight = (config.screenHeightDp - (config.screenHeightDp / 3.4).roundToInt()).dp

    AnimatedContent(state.isDataLoaded, label = "") {
        when (it) {
            true -> LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = MaterialTheme.dimens.large1)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(cardHeight)
                    ) {
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(state.person.coverImage)
                                .build(),
                            contentDescription = null,
                            error = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = FilledUserIcon,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .aspectRatio(1f)
                                    )
                                }
                            },
                            loading = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = FilledUserIcon,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .aspectRatio(1f)
                                    )
                                }
                            }
                        )

                        TopAppBar(
                            title = {},
                            navigationIcon = {
                                AppBackButton(
                                    icon = ArrowUpIcon,
                                    onClick = navigateBack
                                )
                            },
                            windowInsets = WindowInsets(0, 0, 0, 0),
                            scrollBehavior = scroll,
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent,
                                scrolledContainerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.background,
                                            MaterialTheme.colorScheme.background.copy(.8f),
                                            Color.Transparent,
                                        )
                                    )
                                )
                                .padding(
                                    top = MaterialTheme.dimens.large1,
                                    bottom = MaterialTheme.dimens.medium2,
                                    start = MaterialTheme.dimens.medium1
                                )
                        )

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.background,
                                            Color.Transparent
                                        )
                                    )
                                )
                        )
                    }
                }

                detailsCommonContent(state, config, onAction)
            }

            false -> DetailsLoadingScreen(PaddingValues(),cardHeight)
        }
    }
}


@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        Surface {
            PersonCompactScreen(
                state = PersonUiState(
                    person = UiPerson(
                        id = 1,
                        name = "John Doe",
                        coverImage = "",
                        role = "Acting",
                        birthDay = LocalDate.now()
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        deathDay = LocalDate.now()
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        birthPlace = "USA",
                        gender = UiGenderType.MALE,
                        popularity = 5f
                    )
                ),
                onAction = {},
                navigateBack = {}
            )
        }
    }
}