package com.poulastaa.mflix.auth.presentation.email_login

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.poulastaa.mflix.R
import com.poulastaa.mflix.core.presentation.designsystem.theme.CheckIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.EmailIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.EyeCloseIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.EyeOpenIcon
import com.poulastaa.mflix.core.presentation.designsystem.utils.ObserveAsEvent
import com.poulastaa.mflix.core.presentation.designsystem.theme.PasswordIcon
import com.poulastaa.mflix.core.presentation.designsystem.theme.PrevThem
import com.poulastaa.mflix.core.presentation.designsystem.theme.dimens
import com.poulastaa.mflix.core.presentation.ui.AppBackButton
import com.poulastaa.mflix.auth.presentation.email_login.components.DontHaveAccount
import com.poulastaa.mflix.auth.presentation.email_login.components.ForgotPassword
import com.poulastaa.mflix.core.presentation.designsystem.theme.ArrowDownIcon
import com.poulastaa.mflix.core.presentation.ui.SubmitButton

@Composable
fun EmailMediumLogInRootScreen(
    viewModel: EmailLogInViewModel,
    navigateToEmailSingUp: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(viewModel.uiEvent) {
        when (it) {
            is EmailLogInUiEvent.EmitToast -> Toast.makeText(
                context,
                it.message.asString(context),
                Toast.LENGTH_LONG
            ).show()

            is EmailLogInUiEvent.Navigate -> {
                when (it.screen) {
                    EmailLogInUiEvent.NavigationScreen.FORGOT_PASSWORD -> navigateToForgotPassword()
                    EmailLogInUiEvent.NavigationScreen.EMAIL_SIGNUP -> navigateToEmailSingUp()
                }
            }
        }
    }

    EmailMediumLogInScreen(
        state = state,
        onAction = viewModel::onAction,
        navigateBack = navigateBack
    )
}

@Composable
private fun EmailMediumLogInScreen(
    state: EmailLogInUiState,
    onAction: (EmailLogInUiAction) -> Unit,
    navigateBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            AppBackButton(
                modifier = Modifier.padding(start = MaterialTheme.dimens.medium1),
                icon = ArrowDownIcon,
                onClick = navigateBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(MaterialTheme.dimens.medium1),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text( // todo replace with app icon
                    text = stringResource(R.string.app_name),
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(MaterialTheme.dimens.large2))
                Spacer(Modifier.height(MaterialTheme.dimens.large1))

                Text(
                    text = stringResource(R.string.login).lowercase().replace('l', 'L'),
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = MaterialTheme.dimens.small1)
                )

                Spacer(Modifier.height(MaterialTheme.dimens.medium1))

                OutlinedTextField(
                    value = state.email.text,
                    onValueChange = {
                        onAction(EmailLogInUiAction.EmailChanged(it))
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
                    value = state.email.text,
                    onValueChange = {
                        onAction(EmailLogInUiAction.EmailChanged(it))
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
                                onAction(EmailLogInUiAction.OnPasswordVisibilityChange)
                            }
                        ) {
                            AnimatedContent(
                                state.isPasswordVisible,
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
                    visualTransformation = if (state.isPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onAction(EmailLogInUiAction.OnSubmitClick)
                        }
                    )
                )

                Spacer(Modifier.height(MaterialTheme.dimens.small1))

                ForgotPassword(onAction)

                Spacer(Modifier.height(MaterialTheme.dimens.large2))

                DontHaveAccount(
                    text = stringResource(id = R.string.signUp)
                ) {
                    onAction(EmailLogInUiAction.OnEmailSingUpClick)
                }

                Spacer(Modifier.height(MaterialTheme.dimens.large2))

                SubmitButton(
                    modifier = Modifier.fillMaxWidth(.75f),
                    isLoading = state.isMakingApiCall
                ) {
                    onAction(EmailLogInUiAction.OnSubmitClick)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun Preview() {
    PrevThem {
        EmailMediumLogInScreen(
            state = EmailLogInUiState(),
            onAction = {}
        ) { }
    }
}