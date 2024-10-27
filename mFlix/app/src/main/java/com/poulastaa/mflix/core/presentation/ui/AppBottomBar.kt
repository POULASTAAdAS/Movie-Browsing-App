package com.poulastaa.mflix.core.presentation.ui

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.domain.model.BottomBarScreen
import com.poulastaa.mflix.core.presentation.designsystem.AnimatedNavigationBottomBar
import com.poulastaa.mflix.core.presentation.designsystem.model.WiggleButtonItem
import com.poulastaa.mflix.core.presentation.designsystem.repository.Height
import com.poulastaa.mflix.core.presentation.designsystem.repository.Parabolic
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.designsystem.utils.shapeCornerRadius

@Stable
private const val Duration = 500

@Stable
private val wiggleButtonItems = listOf(
    WiggleButtonItem(
        icon = R.drawable.ic_home_empty,
        backgroundIcon = R.drawable.ic_home_filled,
        isSelected = false,
        description = R.string.home,
    ),
    WiggleButtonItem(
        icon = R.drawable.ic_user_empty,
        backgroundIcon = R.drawable.ic_user_filled,
        isSelected = false,
        description = R.string.user
    )
)

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    screen: BottomBarScreen,
    onClick: (BottomBarScreen) -> Unit,
) {
    val haptic = LocalHapticFeedback.current

    AnimatedNavigationBottomBar(
        modifier = modifier
            .height(60.dp),
        selectedIndex = if (screen == BottomBarScreen.HOME) 0 else 1,
        ballColor = MaterialTheme.colorScheme.primary,
        barColor = MaterialTheme.colorScheme.tertiaryContainer,
        cornerRadius = shapeCornerRadius(60.dp),
        ballAnimation = Parabolic(tween(Duration, easing = LinearOutSlowInEasing)),
        indentAnimation = Height(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            animationSpec = tween(
                durationMillis = 700,
                easing = { OvershootInterpolator().getInterpolation(it) })
        )
    ) {
        WiggleButton(
            modifier = Modifier.fillMaxSize(),
            isSelected = screen == BottomBarScreen.HOME,
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick(BottomBarScreen.HOME)
            },
            icon = wiggleButtonItems[0].icon,
            backgroundIcon = wiggleButtonItems[0].backgroundIcon,
            wiggleColor = MaterialTheme.colorScheme.error,
            outlineColor = MaterialTheme.colorScheme.primary,
            contentDescription = stringResource(id = wiggleButtonItems[0].description),
            enterExitAnimationSpec = tween(
                durationMillis = Duration,
                easing = LinearEasing
            ),
            iconSize = 30.dp,
            wiggleAnimationSpec = spring(dampingRatio = .45f, stiffness = 35f)
        )

        WiggleButton(
            modifier = Modifier.fillMaxSize(),
            isSelected = screen == BottomBarScreen.PROFILE,
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick(BottomBarScreen.PROFILE)
            },
            icon = wiggleButtonItems[1].icon,
            backgroundIcon = wiggleButtonItems[1].backgroundIcon,
            wiggleColor = MaterialTheme.colorScheme.error,
            outlineColor = MaterialTheme.colorScheme.primary,
            contentDescription = stringResource(id = wiggleButtonItems[1].description),
            enterExitAnimationSpec = tween(
                durationMillis = Duration,
                easing = LinearEasing
            ),
            iconSize = 30.dp,
            wiggleAnimationSpec = spring(dampingRatio = .45f, stiffness = 35f)
        )
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        Surface {
            AppBottomBar(
                screen = BottomBarScreen.HOME,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimens.medium1)
            ) { }
        }
    }
}