package com.poulastaa.mflix.core.presentation.designsystem

import com.poulastaa.mflix.core.presentation.ui.UiText

data class TextHolder(
    val text: String = "",
    val error: UiText = UiText.DynamicString(""),
    val isError: Boolean = false
)
