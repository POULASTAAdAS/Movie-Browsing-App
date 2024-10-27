package com.poulastaa.mflix.home.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.designsystem.utils.shimmerEffect

@Composable
fun HomeSmallLoadingScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(MaterialTheme.dimens.medium1)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1)
        ) {
            repeat(3) {
                Card(
                    Modifier
                        .width(60.dp)
                        .height(24.dp),
                    shape = MaterialTheme.shapes.extraSmall,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .shimmerEffect()
                    )
                }
            }
        }

        Spacer(Modifier.height(MaterialTheme.dimens.medium3))

        Card(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .fillMaxHeight(.5f)
                .align(Alignment.CenterHorizontally),
            shape = MaterialTheme.shapes.extraSmall,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                shape = MaterialTheme.shapes.extraSmall,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .shimmerEffect()
                )
            }
        }

        Spacer(Modifier.height(MaterialTheme.dimens.medium3))

        repeat(2) {
            Card(
                Modifier
                    .fillMaxWidth(.4f)
                    .height(40.dp),
                shape = MaterialTheme.shapes.extraSmall,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .shimmerEffect()
                )
            }

            Spacer(Modifier.height(MaterialTheme.dimens.medium1))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small3)
            ) {
                repeat(7) {
                    Card(
                        modifier = Modifier
                            .width(140.dp)
                            .fillMaxHeight(),
                        shape = MaterialTheme.shapes.extraSmall,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(MaterialTheme.dimens.small2),
                            shape = MaterialTheme.shapes.extraSmall,
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            )
                        ) {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .shimmerEffect()
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(MaterialTheme.dimens.medium3))
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        HomeSmallLoadingScreen(PaddingValues())
    }
}