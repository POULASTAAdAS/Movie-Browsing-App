package com.poulastaa.mflix.core.presentation.designsystem.model

import com.poulastaa.mflix.core.presentation.designsystem.repository.UiText

data class TextHolder(
    val text: String = "",
    val error: UiText = UiText.DynamicString(""),
    val isError: Boolean = false
)
