package com.poulastaa.mflix.auth.presentation.email_signup

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.poulastaa.mflix.R
import com.poulastaa.mflix.auth.presentation.email_signup.components.AlreadyHaveAnAccount
import com.poulastaa.mflix.core.presentation.designsystem.theme.CheckIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.EmailIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.EyeCloseIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.EyeOpenIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PasswordIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.FilledUserIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.SubmitButton

@Composable
fun EmailSignUpSmallScreen(
    state: EmailSignUpUiState,
    onAction: (EmailSignUpUiAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(MaterialTheme.dimens.medium1)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.signUp),
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = MaterialTheme.dimens.small1)
            )

            Spacer(Modifier.height(MaterialTheme.dimens.medium1))

            OutlinedTextField(
                value = state.userName.text,
                onValueChange = {
                    onAction(EmailSignUpUiAction.OnUserNameChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                label = {
                    Text(text = stringResource(R.string.username))
                },
                isError = state.userName.isError,
                supportingText = {
                    if (state.userName.isError) Text(
                        text = state.userName.error.asString(),
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.ExtraLight
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = FilledUserIcon,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (state.isValidUserName) Icon(
                        imageVector = CheckIcon,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            OutlinedTextField(
                value = state.email.text,
                onValueChange = {
                    onAction(EmailSignUpUiAction.OnEmailChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                label = {
                    Text(text = stringResource(R.string.email))
                },
                isError = state.email.isError,
                supportingText = {
                    if (state.email.isError) Text(
                        text = state.email.error.asString(),
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.ExtraLight
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = EmailIcon,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (state.isValidEmail) Icon(
                        imageVector = CheckIcon,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            OutlinedTextField(
                value = state.password.text,
                onValueChange = {
                    onAction(EmailSignUpUiAction.OnPasswordChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                label = {
                    Text(text = stringResource(R.string.password))
                },
                isError = state.password.isError,
                supportingText = {
                    if (state.password.isError) Text(
                        text = state.password.error.asString(),
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.ExtraLight
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = PasswordIcon,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onAction(EmailSignUpUiAction.OnTogglePasswordVisibilityClicked)
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                    ) {
                        AnimatedContent(
                            state.isValidPassword,
                            label = "password visibility animation"
                        ) {
                            when (it) {
                                true -> Icon(
                                    imageVector = EyeCloseIcon,
                                    contentDescription = null
                                )

                                false -> Icon(
                                    imageVector = EyeOpenIcon,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                },
                visualTransformation = if (state.isValidPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            OutlinedTextField(
                value = state.conformPassword.text,
                onValueChange = {
                    onAction(EmailSignUpUiAction.OnConformPasswordChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                label = {
                    Text(text = stringResource(R.string.conform_password))
                },
                isError = state.conformPassword.isError,
                supportingText = {
                    if (state.conformPassword.isError) Text(
                        text = state.conformPassword.error.asString(),
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.ExtraLight
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = PasswordIcon,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (state.areSamePassword) Icon(
                        imageVector = CheckIcon,
                        contentDescription = null
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onAction(EmailSignUpUiAction.OnSubmitClicked)
                    }
                )
            )

            Spacer(Modifier.height(MaterialTheme.dimens.large1))

            AlreadyHaveAnAccount(onAction)

            Spacer(Modifier.height(MaterialTheme.dimens.large2))

            SubmitButton(
                modifier = Modifier.fillMaxWidth(.75f),
                isLoading = state.isMakingApiCall
            ) {
                onAction(EmailSignUpUiAction.OnSubmitClicked)
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        EmailSignUpSmallScreen(
            state = EmailSignUpUiState()
        ) { }
    }
}