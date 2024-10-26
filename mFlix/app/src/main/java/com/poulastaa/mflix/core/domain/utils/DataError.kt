package com.poulastaa.mflix.core.domain.utils

interface DataError : Error {
    enum class Network : DataError {
        UNAUTHORISED,
        NOT_FOUND,
        CONFLICT,
        NO_INTERNET,
        SERVER_ERROR,
        UNKNOWN
    }
}