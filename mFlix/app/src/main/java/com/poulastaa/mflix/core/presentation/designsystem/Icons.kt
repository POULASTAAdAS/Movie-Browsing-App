package com.poulastaa.mflix.core.presentation.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.poulastaa.mflix.R

val ArrowBackIcon: ImageVector
    @Composable
    get() = Icons.AutoMirrored.Rounded.ArrowBack


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

val UserIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.ic_user)