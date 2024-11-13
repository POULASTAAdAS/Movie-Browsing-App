package com.poulastaa.mflix.settings

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.presentation.designsystem.theme.ArrowBackIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.CheckIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.CloseIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.LogoutIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.AppBackButton
import com.poulastaa.mflix.settings.components.SettingsHeading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingCompactScreen(
    state: SettingUiState,
    onAction: (SettingUiAction) -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings),
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    AppBackButton(
                        icon = ArrowBackIcon,
                        onClick = navigateBack
                    )
                }
            )
        }
    ) { passingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(passingValues)
                .padding(MaterialTheme.dimens.medium1)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraSmall,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.small3),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SettingsFrontSpace()

                    Spacer(Modifier.width(MaterialTheme.dimens.medium1))

                    SettingsHeading(R.string.include_addult)

                    Spacer(Modifier.weight(1f))

                    Switch(
                        checked = state.toggleState,
                        onCheckedChange = {
                            onAction(SettingUiAction.OnAdultTypeToggle)
                        },
                        thumbContent = {
                            AnimatedContent(state.toggleState, label = "") {
                                when (it) {
                                    true -> Icon(
                                        imageVector = CheckIcon,
                                        contentDescription = null
                                    )

                                    false -> Icon(
                                        imageVector = CloseIcon,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    )
                }
            }

            Spacer(Modifier.height(MaterialTheme.dimens.medium1))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraSmall,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.small3),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SettingsFrontSpace()

                    Spacer(Modifier.width(MaterialTheme.dimens.medium1))

                    SettingsHeading(R.string.logout)

                    Spacer(Modifier.weight(1f))

                    FilledTonalIconButton(
                        onClick = {

                        },
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Icon(
                            imageVector = LogoutIcon,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsFrontSpace() {
    TODO("Not yet implemented")
}


@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        Surface {
            SettingCompactScreen(SettingUiState(), {}) { }
        }
    }
}