package com.poulastaa.mflix.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poulastaa.mflix.core.domain.model.BottomBarScreen
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoreViewmodel @Inject constructor(
    private val ds: DataStoreRepository,
) : ViewModel() {
    var state by mutableStateOf(CoreUiState())
        private set

    fun changeScreen(bottomBarScreen: BottomBarScreen) {
        if (state.bottomBarScreen == bottomBarScreen) return

        state = when (bottomBarScreen) {
            BottomBarScreen.HOME -> state.copy(
                screen = AppScreen.Home
            )

            BottomBarScreen.PROFILE -> state.copy(
                screen = AppScreen.Profile
            )
        }
    }
}