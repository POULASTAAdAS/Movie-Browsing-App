package com.poulastaa.core.utils

object Constants {
    private const val SECURITY_TYPE_EMAIL = "jwt-auth"
    private const val SECURITY_TYPE_SESSION = "session-auth"

    val SECURITY_LIST = arrayOf(SECURITY_TYPE_EMAIL, SECURITY_TYPE_SESSION)

    const val SESSION_NAME_GOOGLE = "GOOGLE_USER_SESSION"
}