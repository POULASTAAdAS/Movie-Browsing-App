package com.poulastaa.mflix.profile.presentation

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.presentation.designsystem.MovingCirclesWithMetaballEffect
import com.poulastaa.mflix.core.presentation.designsystem.theme.EditIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.EmailIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.FilledUserIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.designsystem.utils.shimmerEffect
import com.poulastaa.mflix.profile.presentation.components.ProfileTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSmallExtendedScreen(
    state: ProfileUiState,
    onAction: (ProfileUiAction) -> Unit,
) {
    val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        AnimatedContent(!state.hasData, label = "") {
            when (it) {
                true -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .nestedScroll(scroll.nestedScrollConnection)
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(.4f)
                                .padding(MaterialTheme.dimens.medium1)
                                .padding(top = 30.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Card(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .padding(MaterialTheme.dimens.large1),
                                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                                shape = RoundedCornerShape(
                                    topEnd = 140.dp,
                                    bottomStart = 140.dp,
                                    topStart = 40.dp,
                                    bottomEnd = 40.dp
                                )
                            ) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    MovingCirclesWithMetaballEffect()

                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(MaterialTheme.dimens.large1),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Box(modifier = Modifier.fillMaxSize()) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Card(
                                                    modifier = Modifier
                                                        .padding(bottom = MaterialTheme.dimens.medium1)
                                                        .fillMaxSize(.75f)
                                                        .aspectRatio(1f),
                                                    shape = CircleShape,
                                                    elevation = CardDefaults.cardElevation(
                                                        defaultElevation = 15.dp
                                                    )
                                                ) {
                                                    SubcomposeAsyncImage(
                                                        model = ImageRequest.Builder(LocalContext.current)
                                                            .data(state.user.coverImage)
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
                                                    onClick = {
                                                        onAction(ProfileUiAction.OnEditClick)
                                                    },
                                                    modifier = Modifier
                                                        .align(Alignment.BottomEnd)
                                                        .fillMaxWidth(.5f)
                                                        .fillMaxHeight(.6f),
                                                ) {
                                                    Icon(
                                                        imageVector = EditIcon,
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .clip(CircleShape)
                                                            .background(MaterialTheme.colorScheme.background)
                                                            .padding(MaterialTheme.dimens.small2),
                                                        tint = MaterialTheme.colorScheme.primary
                                                    )
                                                }
                                            }

                                            Card(
                                                modifier = Modifier
                                                    .align(Alignment.BottomCenter),
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
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    modifier = Modifier
                                                        .padding(horizontal = MaterialTheme.dimens.small3)
                                                        .padding(vertical = MaterialTheme.dimens.small1)
                                                        .align(Alignment.CenterHorizontally),
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                shape = MaterialTheme.shapes.small,
                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = MaterialTheme.dimens.small1),
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .padding(start = MaterialTheme.dimens.small2)
                                            .padding(vertical = MaterialTheme.dimens.small2)
                                            .fillMaxHeight()
                                            .width(3.dp)
                                            .background(MaterialTheme.colorScheme.primary)
                                    )

                                    Card(
                                        modifier = Modifier.padding(MaterialTheme.dimens.small1),
                                        shape = CircleShape,
                                        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                        )
                                    ) {
                                        Icon(
                                            imageVector = EmailIcon,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(MaterialTheme.dimens.small2),
                                        )
                                    }

                                    Spacer(Modifier.width(MaterialTheme.dimens.small2))

                                    Text(
                                        text = state.user.email,
                                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.CenterVertically),
                                    )
                                }
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(MaterialTheme.dimens.medium1)
                                .padding(top = 35.dp)
                        ) {
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
                        }
                    }

                    ProfileTopBar(scroll)
                }

                false -> LoadingScreen(paddingValues, scroll)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadingScreen(
    paddingValues: PaddingValues,
    scroll: TopAppBarScrollBehavior,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .nestedScroll(scroll.nestedScrollConnection)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.4f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(top = MaterialTheme.dimens.medium3)
                        .aspectRatio(1f)
                        .padding(MaterialTheme.dimens.large2)
                        .padding(MaterialTheme.dimens.medium1),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    shape = RoundedCornerShape(
                        topEnd = 100.dp,
                        bottomStart = 100.dp,
                        topStart = 40.dp,
                        bottomEnd = 40.dp
                    )
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(MaterialTheme.dimens.large1),
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .shimmerEffect()
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth(.7f)
                        .height(30.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    shape = MaterialTheme.shapes.extraSmall
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shimmerEffect()
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = MaterialTheme.dimens.medium1)
                    .padding(top = 80.dp)
            ) {
                repeat(2) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(.3f)
                            .height(25.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
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
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2)
                    ) {
                        repeat(7) {
                            Card(
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(180.dp),
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
            }
        }
        ProfileTopBar(scroll)
    }
}