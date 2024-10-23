package com.poulastaa.mflix.auth.presentation.email_signup

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
class EmailSignUpViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(EmailSignUpUiState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<EmailSignUpEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: EmailSignUpUiAction) {
        when (action) {
            is EmailSignUpUiAction.OnUserNameChanged -> {
                _state.update {
                    it.copy(
                        userName = it.userName.copy(
                            text = action.newUserName,
                            isError = false,
                        )
                    )
                }
            }

            is EmailSignUpUiAction.OnEmailChanged -> {
                _state.update {
                    it.copy(
                        email = it.email.copy(
                            text = action.newEmail,
                            isError = false,
                        )
                    )
                }
            }

            is EmailSignUpUiAction.OnPasswordChanged -> {
                _state.update {
                    it.copy(
                        password = it.password.copy(
                            text = action.newPassword,
                            isError = false,
                        )
                    )
                }
            }

            is EmailSignUpUiAction.OnConformPasswordChanged -> {
                _state.update {
                    it.copy(
                        conformPassword = it.conformPassword.copy(
                            text = action.newPassword,
                            isError = false,
                        )
                    )
                }
            }

            EmailSignUpUiAction.OnTogglePasswordVisibilityClicked -> {
                _state.update {
                    it.copy(
                        passwordVisibility = it.passwordVisibility.not()
                    )
                }
            }

            EmailSignUpUiAction.OnLogInClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(EmailSignUpEvent.NavigateToEmailLogIn)
                }
            }

            EmailSignUpUiAction.OnSubmitClicked -> {
                if (_state.value.isMakingApiCall) return

                _state.update {
                    it.copy(
                        isMakingApiCall = true
                    )
                }
            }
        }
    }
}