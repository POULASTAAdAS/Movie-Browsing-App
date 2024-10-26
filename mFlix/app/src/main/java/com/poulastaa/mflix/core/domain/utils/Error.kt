package com.poulastaa.mflix.core.domain.utils

interface Error

data object NoInternetException : Exception() {
    private fun readResolve(): Any = NoInternetException
}

data object OtherRemoteException : Exception() {
    private fun readResolve(): Any = OtherRemoteException
}