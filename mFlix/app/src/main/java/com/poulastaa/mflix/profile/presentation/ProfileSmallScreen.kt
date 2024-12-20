package com.poulastaa.mflix.profile.presentation

import android.provider.Settings
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.UiPrevItem
import com.poulastaa.mflix.core.domain.model.UiUser
import com.poulastaa.mflix.core.presentation.designsystem.MovingCirclesWithMetaballEffect
import com.poulastaa.mflix.core.presentation.designsystem.theme.EditIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.FilledUserIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.designsystem.utils.shimmerEffect
import com.poulastaa.mflix.core.presentation.ui.PrevItemCard
import com.poulastaa.mflix.profile.presentation.components.ProfileTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSmallScreen(
    state: ProfileUiState,
    onAction: (ProfileUiAction) -> Unit,
) {
    val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val cardHeight =
        (LocalConfiguration.current.screenHeightDp - LocalConfiguration.current.screenHeightDp / 2).dp
    val context = LocalContext.current

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        AnimatedContent(state.hasData, label = "") {
            when (it) {
                true -> Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .nestedScroll(scroll.nestedScrollConnection)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(cardHeight)
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(.7f)
                                .fillMaxHeight()
                                .align(Alignment.TopCenter),
                            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                            shape = RoundedCornerShape(
                                bottomStart = 100.dp,
                                bottomEnd = 100.dp
                            )
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                MovingCirclesWithMetaballEffect()

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 50.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    ProfileImage(state.user) {
                                        onAction(ProfileUiAction.OnEditClick)
                                    }
                                }
                            }
                        }

                        ProfileTopBar(scroll) {
                            onAction(ProfileUiAction.OnSettingClick)
                        }

                        Card(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth(.5f)
                                .offset(y = 20.dp),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                contentColor = MaterialTheme.colorScheme.primary,
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 10.dp
                            )
                        ) {
                            Text(
                                text = state.user.name,
                                fontWeight = FontWeight.Medium,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .padding(horizontal = MaterialTheme.dimens.medium1)
                                    .padding(vertical = MaterialTheme.dimens.small3)
                                    .align(Alignment.CenterHorizontally),
                            )
                        }
                    }

                    Spacer(Modifier.height(MaterialTheme.dimens.medium1))

                    ProfileContent(
                        title = R.string.favourites,
                        list = state.favourite,
                        onAction = onAction
                    )

                    ProfileContent(
                        title = R.string.upcoming_movies,
                        list = state.upcomingMovie,
                        onAction = onAction
                    )

                    ProfileContent(
                        title = R.string.upcoming_tv,
                        list = state.upcomingTv,
                        onAction = onAction
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(
                                if (Settings.Secure.getInt(
                                        context.contentResolver,
                                        "navigation_mode",
                                        0
                                    ) == 2
                                ) 60.dp
                                else MaterialTheme.dimens.medium1
                            )
                            .navigationBarsPadding()
                    )
                }

                false -> ProfileLoading(cardHeight, scroll)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileLoading(
    cardHeight: Dp,
    scroll: TopAppBarScrollBehavior,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .fillMaxHeight()
                    .align(Alignment.TopCenter),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(
                    bottomStart = 100.dp,
                    bottomEnd = 100.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmerEffect()
                ) {
                    MovingCirclesWithMetaballEffect()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                    }
                }
            }

            ProfileTopBar(scroll) {}

            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(.5f)
                    .offset(y = 20.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .shimmerEffect()
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.medium3)
                )
            }

        }

        Spacer(Modifier.height(MaterialTheme.dimens.large2))

        repeat(2) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .height(40.dp)
                    .padding(start = MaterialTheme.dimens.medium1),
                shape = MaterialTheme.shapes.extraSmall
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmerEffect()
                )
            }

            Spacer(Modifier.height(MaterialTheme.dimens.medium1))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MaterialTheme.dimens.medium1)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3)
            ) {
                repeat(7) {
                    Card(
                        modifier = Modifier
                            .width(140.dp)
                            .height(200.dp),
                        shape = MaterialTheme.shapes.extraSmall
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .shimmerEffect()
                        )
                    }
                }
            }

            Spacer(Modifier.height(MaterialTheme.dimens.medium1))
        }

        Spacer(Modifier.height(120.dp))
    }
}

@Composable
fun ProfileContent(
    @StringRes title: Int,
    list: List<UiPrevItem>,
    onAction: (ProfileUiAction) -> Unit,
) {
    if (list.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.medium1)
        ) {
            Text(
                text = stringResource(title),
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
        }

        ProfileLazyRow {
            items(list) { item ->
                PrevItemCard(
                    modifier = Modifier
                        .width(140.dp)
                        .height(200.dp),
                    item = item,
                    onClick = {
                        onAction(ProfileUiAction.OnItemClick(item.id, item.type))
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileLazyRow(
    content: LazyListScope.() -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.medium1),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3)
    ) {
        content()
    }
}

@Composable
fun ProfileImage(
    user: UiUser,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(bottom = MaterialTheme.dimens.medium1)
                .fillMaxSize(.65f)
                .aspectRatio(1f),
            shape = CircleShape,
            onClick = onClick,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            )
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.coverImage)
                    .build(),
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                error = {
                    Icon(
                        imageVector = FilledUserIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .padding(MaterialTheme.dimens.medium1)
                    )
                },
                loading = {
                    Icon(
                        imageVector = FilledUserIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .padding(MaterialTheme.dimens.medium1)
                    )
                }
            )
        }

        IconButton(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth(.6f)
                .fillMaxHeight(.63f),
        ) {
            Icon(
                imageVector = EditIcon,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(MaterialTheme.dimens.medium1),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        Surface {
            ProfileSmallScreen(
                state = ProfileUiState(
                    user = UiUser(
                        name = "Poulastaa Das"
                    ),
                    favourite = (1..10).map {
                        UiPrevItem(
                            id = it.toLong(),
                            title = "That Cool Movie",
                            description = "That cool movie description",
                            rating = 5.7,
                            isInFavourite = true,
                        )
                    },
                    upcomingMovie = (1..10).map {
                        UiPrevItem(
                            id = it.toLong(),
                            title = "That Cool Movie",
                            description = "That cool movie description",
                            rating = 5.7,
                            isInFavourite = true,
                        )
                    },
                    upcomingTv = (1..10).map {
                        UiPrevItem(
                            id = it.toLong(),
                            title = "That Cool Tv show",
                            description = "That cool Tv show description",
                            rating = 5.7,
                            isInFavourite = true,
                        )
                    }
                )
            ) { }
        }
    }
}