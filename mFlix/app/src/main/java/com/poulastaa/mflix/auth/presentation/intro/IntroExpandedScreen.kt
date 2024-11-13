package com.poulastaa.mflix.auth.presentation.intro

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poulastaa.mflix.R
import com.poulastaa.mflix.auth.presentation.intro.components.IntroButton
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.designsystem.utils.ObserveAsEvent

@Composable
fun IntroRootExpandedScreen(
    viewmodel: IntroViewmodel,
) {
    val state by viewmodel.state.collectAsState()

    IntroExpandedScreen(
        state = state,
        onAction = viewmodel::onAction
    )
}

@Composable
private fun IntroExpandedScreen(
    state: IntroUiState,
    onAction: (IntroUiAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            // todo add image
        }

        Row(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.4f)
                    .padding(MaterialTheme.dimens.medium1),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = MaterialTheme.typography.displayMedium.fontSize,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 8.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(MaterialTheme.dimens.medium1))

                Text(
                    text = stringResource(R.string.intro_desc),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.medium1),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IntroButton(
                    isLoading = state.isMakingApiCall,
                    title = stringResource(R.string.continue_with_google),
                    icon = {
                        Image(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.background),
                            painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = null
                        )
                    },
                    onClick = {
                        onAction(IntroUiAction.OnGoogleAuthClick)
                    }
                )

                Spacer(Modifier.height(MaterialTheme.dimens.large1))

                IntroButton(
                    title = stringResource(R.string.continue_with_email),
                    icon = {
                        Image(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.background)
                                .padding(MaterialTheme.dimens.small2),
                            painter = painterResource(id = R.drawable.ic_email),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    },
                    onClick = {
                        onAction(IntroUiAction.OnEmailClick)
                    }
                )
            }
        }
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 840,
    heightDp = 360
)
@Preview(
    widthDp = 840,
    heightDp = 360
)
@Composable
private fun Preview() {
    PrevThem {
        IntroExpandedScreen(
            state = IntroUiState()
        ) { }
    }
}