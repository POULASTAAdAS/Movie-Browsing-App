package com.poulastaa.mflix.person.repsentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.poulastaa.mflix.core.presentation.designsystem.theme.BDayIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.DDayIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.FemaleIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.GenderOtherIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.LocationIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.MaleIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PopularIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.animateText
import com.poulastaa.mflix.person.data.model.UiGenderType
import com.poulastaa.mflix.person.repsentation.UiPerson
import java.util.Locale


@Composable
@OptIn(ExperimentalLayoutApi::class)
fun DetailsItemFlowRow(
    person: UiPerson,
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.dimens.medium3),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1)
    ) {
        PersonItemCard(
            title = if (UiGenderType.FEMALE == person.gender) person.gender.value
            else animateText(person.gender.value),
            icon = when (person.gender) {
                UiGenderType.MALE -> MaleIcon
                UiGenderType.FEMALE -> FemaleIcon
                UiGenderType.OTHER -> GenderOtherIcon
            }
        )

        person.birthDay?.let {
            PersonItemCard(
                title = animateText(it),
                icon = BDayIcon
            )
        }

        person.deathDay?.let {
            PersonItemCard(
                title = animateText(it),
                icon = DDayIcon
            )
        }

        person.birthPlace?.let {
            PersonItemCard(
                title = animateText(it),
                icon = LocationIcon,
                modifier = Modifier.rotate(45f)
            )

        }


        val popularity by animateFloatAsState(
            targetValue = person.popularity,
            animationSpec = tween(1000),
            label = ""
        )

        PersonItemCard(
            title = String.format(Locale.getDefault(), "%.2f", popularity),
            icon = PopularIcon,
        )
    }
}

@Composable
private fun PersonItemCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    val config = LocalConfiguration.current

    Card(
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(
                    if (config.screenWidthDp > 980) MaterialTheme.dimens.small2
                    else MaterialTheme.dimens.small3
                )
                .padding(
                    horizontal = if (config.screenWidthDp > 980) MaterialTheme.dimens.small2
                    else MaterialTheme.dimens.small3
                ),
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
                        .padding(
                            if (config.screenWidthDp > 980) MaterialTheme.dimens.small1
                            else MaterialTheme.dimens.small2
                        ),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.width(MaterialTheme.dimens.small3))

            Text(text = title)
        }
    }
}