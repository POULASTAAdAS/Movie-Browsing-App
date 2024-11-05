package com.poulastaa.mflix.person.repsentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.core.presentation.designsystem.theme.ArrowUpIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.AppBackButton
import com.poulastaa.mflix.core.presentation.ui.animateText
import com.poulastaa.mflix.details.presentation.components.DetailsLoadingScreen
import com.poulastaa.mflix.person.data.model.UiGenderType
import com.poulastaa.mflix.person.repsentation.components.DetailsItemFlowRow
import com.poulastaa.mflix.person.repsentation.components.detailsCommonContent
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PersonExtendedScreen(
    state: PersonUiState,
    onAction: (PersonUiAction) -> Unit,
    navigateBack: () -> Unit,
) {
    val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val config = LocalConfiguration.current
    val cardHeight = (config.screenHeightDp - config.screenHeightDp / 9).dp

    AnimatedContent(state.isDataLoaded, label = "") {
        when (it) {
            true -> LazyVerticalGrid(
                columns = GridCells.Fixed(6),
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    val image = ImageRequest.Builder(LocalContext.current)
                        .data(state.person.coverImage.replace("w500", "original"))
                        .build()

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(cardHeight)
                    ) {
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .fillMaxSize()
                                .blur(40.dp),
                            contentScale = ContentScale.FillBounds,
                            model = image,
                            contentDescription = null,
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
                                .padding(MaterialTheme.dimens.medium1)
                        )

                        Spacer(
                            modifier = Modifier.Companion
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(120.dp)
                                .offset(y = 60.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            if (isSystemInDarkTheme())
                                                MaterialTheme.colorScheme.background
                                            else MaterialTheme.colorScheme.background,
                                            Color.Transparent
                                        )
                                    )
                                )
                        )

                        Spacer(
                            modifier = Modifier.Companion
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            MaterialTheme.colorScheme.background,
                                        )
                                    )
                                )
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(60.dp + MaterialTheme.dimens.small2)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(.4f)
                            ) {
                                Card(
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 10.dp
                                    ),
                                    shape = MaterialTheme.shapes.extraSmall,
                                    colors = if (isSystemInDarkTheme()) CardDefaults.cardColors()
                                    else CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.background
                                    )
                                ) {
                                    Card(
                                        modifier = Modifier.padding(MaterialTheme.dimens.small2),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 10.dp
                                        ),
                                        shape = MaterialTheme.shapes.extraSmall
                                    ) {
                                        SubcomposeAsyncImage(
                                            model = image,
                                            contentDescription = null,
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                }
                            }

                            Card(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                shape = MaterialTheme.shapes.extraSmall,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.background.copy(
                                        .4f
                                    )
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(MaterialTheme.dimens.medium1)
                                ) {
                                    Text(
                                        text = "${animateText(animateText(state.person.name))} (${
                                            animateText(
                                                state.person.role
                                            )
                                        })",
                                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    if (config.screenWidthDp > 980) DetailsItemFlowRow(state.person)
                                }
                            }
                        }
                    }
                }

                detailsCommonContent(state, config, onAction)
            }

            false -> DetailsLoadingScreen(PaddingValues(), cardHeight)
        }
    }
}


@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        Surface {
            PersonExtendedScreen(
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