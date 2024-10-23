package com.poulastaa.mflix.auth.presentation.email_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailLogInViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(EmailLogInUiState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<EmailLogInUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: EmailLogInUiAction) {
        when (action) {
            is EmailLogInUiAction.EmailChanged -> {
                _state.update {
                    it.copy(
                        email = it.email.copy(
                            text = action.newEmail,
                            isError = false
                        )
                    )
                }
            }

            is EmailLogInUiAction.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = it.password.copy(
                            text = action.newPassword,
                            isError = false
                        )
                    )
                }
            }

            EmailLogInUiAction.OnSubmitClick -> {
                if (_state.value.isMakingApiCall) return

                _state.update {
                    it.copy(
                        isMakingApiCall = true
                    )
                }
            }

            EmailLogInUiAction.OnEmailSingUpClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        EmailLogInUiEvent.Navigate(
                            EmailLogInUiEvent.NavigationScreen.EMAIL_SIGNUP
                        )
                    )
                }
            }

            EmailLogInUiAction.OnForgotPasswordClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        EmailLogInUiEvent.Navigate(
                            EmailLogInUiEvent.NavigationScreen.FORGOT_PASSWORD
                        )
                    )
                }
            }

            EmailLogInUiAction.OnPasswordVisibilityChange -> {
                _state.update {
                    it.copy(
                        isPasswordVisible = !it.isPasswordVisible
                    )
                }
            }
        }
    }
}