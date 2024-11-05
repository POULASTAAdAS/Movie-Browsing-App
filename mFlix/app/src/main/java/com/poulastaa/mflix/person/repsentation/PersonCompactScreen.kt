package com.poulastaa.mflix.person.repsentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.presentation.designsystem.theme.ArrowUpIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.BDayIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.DDayIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.FemaleIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.FilledUserIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.GenderOtherIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.LocationIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.MaleIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PopularIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.AppBackButton
import com.poulastaa.mflix.core.presentation.ui.PrevItemCard
import com.poulastaa.mflix.core.presentation.ui.animateText
import com.poulastaa.mflix.person.data.model.UiGenderType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PersonCompactScreen(
    state: PersonUiState,
    onAction: (PersonUiAction) -> Unit,
    navigateBack: () -> Unit,
) {
    val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val config = LocalConfiguration.current
    val cardHeight = (config.screenHeightDp - (config.screenHeightDp / 3.4).roundToInt()).dp

    LazyVerticalGrid(
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

        item(span = { GridItemSpan(maxLineSpan) }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 35.dp)
                    .offset(y = (-34).dp),
                shape = RoundedCornerShape(
                    topStart = 34.dp,
                    topEnd = 34.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.medium1)
                        .padding(top = 24.dp)
                ) {
                    Text(
                        text = "${animateText(animateText(state.person.name))} (${animateText(state.person.role)})",
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )

                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = MaterialTheme.dimens.medium3),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1)
                    ) {
                        PersonItemCard(
                            title = if (UiGenderType.FEMALE == state.person.gender) state.person.gender.value
                            else animateText(state.person.gender.value),
                            icon = when (state.person.gender) {
                                UiGenderType.MALE -> MaleIcon
                                UiGenderType.FEMALE -> FemaleIcon
                                UiGenderType.OTHER -> GenderOtherIcon
                            }
                        )

                        state.person.birthDay?.let {
                            PersonItemCard(
                                title = animateText(it),
                                icon = BDayIcon
                            )
                        }

                        state.person.deathDay?.let {
                            PersonItemCard(
                                title = animateText(it),
                                icon = DDayIcon
                            )
                        }

                        state.person.birthPlace?.let {
                            PersonItemCard(
                                title = animateText(it),
                                icon = LocationIcon,
                                modifier = Modifier.rotate(45f)
                            )

                        }


                        val popularity by animateFloatAsState(
                            targetValue = state.person.popularity,
                            animationSpec = tween(1000),
                            label = ""
                        )

                        PersonItemCard(
                            title = String.format(Locale.getDefault(), "%.2f", popularity),
                            icon = PopularIcon,
                        )
                    }
                }
            }
        }

        if (state.knownFor.isNotEmpty()) item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.medium1)
            ) {
                Text(
                    text = stringResource(R.string.popular_collection),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = MaterialTheme.colorScheme.primary.copy(.7f),
                    textDecoration = TextDecoration.Underline
                )
            }
        }

        items(state.knownFor) { item ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.small3)
            ) {
                PrevItemCard(
                    item = item,
                    modifier = Modifier
                        .height(200.dp)
                        .padding(MaterialTheme.dimens.small2)
                ) {
                    onAction(PersonUiAction.OnItemClick(item.id, item.type))
                }
            }
        }
    }
}


@Composable
private fun PersonItemCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(MaterialTheme.dimens.small3)
                .padding(horizontal = MaterialTheme.dimens.small3),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                ),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = modifier
                        .padding(MaterialTheme.dimens.small2),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.width(MaterialTheme.dimens.small3))

            Text(text = title)
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