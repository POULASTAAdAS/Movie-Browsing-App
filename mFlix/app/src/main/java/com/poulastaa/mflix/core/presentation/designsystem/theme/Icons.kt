package com.poulastaa.mflix.core.presentation.designsystem.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.poulastaa.mflix.R

val ArrowDownIcon: ImageVector
    @Composable
    get() = Icons.Rounded.KeyboardArrowDown

val CheckIcon: ImageVector
    @Composable
    get() = Icons.Rounded.Check

val EyeOpenIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_visibility_on)

val EyeCloseIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_visibility_off)

val EmailIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_email)

val PasswordIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_password)

val FilledUserIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_user_filled)

val EmptyUserIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_user_filled)

val MovieIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_movie)

val FavoriteEmptyIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_favorite_empty)

val FavoriteFillIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_favorite_fill)

val SearchIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_search)

val EditIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_edit)