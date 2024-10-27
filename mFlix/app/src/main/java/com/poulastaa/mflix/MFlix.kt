package com.poulastaa.mflix

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

@HiltAndroidApp
class MFlix : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun applicationScope() = applicationScope

    override fun onTerminate() {
        super.onTerminate()
        applicationScope.cancel()
    }
}