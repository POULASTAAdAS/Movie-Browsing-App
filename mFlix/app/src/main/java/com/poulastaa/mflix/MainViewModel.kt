package com.poulastaa.mflix

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poulastaa.mflix.core.domain.model.RouteExt
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val ds: DataStoreRepository
) : ViewModel() {
    private val _route = MutableStateFlow<RouteExt?>(null)
    val route = _route
        .onStart { loadStartScreen() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(4000L),
            initialValue = null
        )

    private val _keepSplashOn = MutableStateFlow(true)
    val keepSplashOn = _keepSplashOn.asStateFlow()

    private fun loadStartScreen() {
        viewModelScope.launch {
            when (ds.readSignInState()) {
                Screen.EmailLogIn,
                Screen.ForgotPassword,
                Screen.EmailSignUp,
                Screen.Intro -> RouteExt(Screen.Intro)

                Screen.App -> RouteExt(Screen.App)
            }.let { route ->
                _route.update { route }
                if (_route.value != null) _keepSplashOn.value = false
            }
        }
    }
}