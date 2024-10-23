package com.poulastaa.mflix.auth.presentation.intro

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poulastaa.mflix.R
import com.poulastaa.mflix.auth.presentation.intro.components.IntroButton
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.designsystem.ObserveAsEvent

@Composable
fun IntroRootMediumScreen(
    viewmodel: IntroViewmodel,
    navigateToEmailLogIn: () -> Unit
) {
    val context = LocalContext.current
    val state by viewmodel.state.collectAsState()

    ObserveAsEvent(viewmodel.uiEvent) {
        when (it) {
            is IntroUiEvent.EmitToast -> Toast.makeText(
                context,
                it.message.asString(context),
                Toast.LENGTH_LONG
            ).show()

            IntroUiEvent.NavigateToEmailLogIn -> navigateToEmailLogIn()

            IntroUiEvent.StartGoogleAuth -> {

            }
        }
    }

    IntroMediumScreen(
        state = state,
        onAction = viewmodel::onAction
    )
}

@Composable
private fun IntroMediumScreen(
    state: IntroUiState,
    onAction: (IntroUiAction) -> Unit
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimens.medium1)
                .padding(bottom = MaterialTheme.dimens.medium1),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Spacer(Modifier.weight(2f))

            Text(
                text = stringResource(R.string.app_name),
                fontSize = MaterialTheme.typography.displayLarge.fontSize,
                fontWeight = FontWeight.Black,
                letterSpacing = 8.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(MaterialTheme.dimens.medium1))

            Text(
                text = stringResource(R.string.intro_desc),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.weight(1f))

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
                    onAction(IntroUiAction.OnGoogleClick)
                }
            )

            Spacer(Modifier.height(MaterialTheme.dimens.medium2))

            IntroButton(
                title = stringResource(R.string.continue_with_email),
                icon = {
                    Image(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.background)
                            .padding(MaterialTheme.dimens.small1),
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


@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        IntroMediumScreen(
            state = IntroUiState()
        ) { }
    }
}