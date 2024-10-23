package com.poulastaa.mflix.auth.presentation.email_login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.poulastaa.mflix.R
import com.poulastaa.mflix.auth.presentation.email_login.EmailLogInUiAction


@Composable
fun ForgotPassword(onAction: (EmailLogInUiAction.OnForgotPasswordClick) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = stringResource(R.string.forgot_password),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = null
            ) {
                onAction(EmailLogInUiAction.OnForgotPasswordClick)
            }
        )
    }
}