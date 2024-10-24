package com.poulastaa.mflix.auth.presentation.email_signup.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.poulastaa.mflix.R
import com.poulastaa.mflix.auth.presentation.email_signup.EmailSignUpUiAction
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens

@Composable
fun AlreadyHaveAnAccount(onAction: (EmailSignUpUiAction.OnLogInClicked) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.already_have_an_account))

        Spacer(modifier = Modifier.width(MaterialTheme.dimens.small2))

        Text(
            text = stringResource(R.string.login),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null,
                onClick = {
                    onAction(EmailSignUpUiAction.OnLogInClicked)
                }
            )
        )
    }
}