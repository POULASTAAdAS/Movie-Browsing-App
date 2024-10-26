package com.poulastaa.mflix.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.poulastaa.mflix.core.data.model.LocalUserSerializable
import com.poulastaa.mflix.core.data.model.toLocalUser
import com.poulastaa.mflix.core.data.model.toUserSerializable
import com.poulastaa.mflix.core.domain.model.LocalUser
import com.poulastaa.mflix.core.domain.repository.DataStoreRepository
import com.poulastaa.mflix.core.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PreferencesDatastore @Inject constructor(
    private val ds: DataStore<Preferences>
) : DataStoreRepository {
    private object PreferencesKeys {
        val COOKIE = stringPreferencesKey(name = "cookie")
        val SIGN_IN_STATE = stringPreferencesKey(name = "sign_in_state")
        val LOCAL_USER = stringPreferencesKey(name = "local_user")
    }

    override suspend fun storeCookie(cookie: String) {
        ds.edit {
            it[PreferencesKeys.COOKIE] = cookie
        }
    }

    override fun readCookie(): Flow<String> = ds.data.catch {
        emit(emptyPreferences())
    }.map {
        it[PreferencesKeys.COOKIE] ?: ""
    }

    override suspend fun storeSignInState(screen: Screen) {
        ds.edit {
            it[PreferencesKeys.SIGN_IN_STATE] = screen.toString()
        }
    }

    override suspend fun readSignInState(): Screen = ds.data.catch {
        emit(emptyPreferences())
    }.map {
        val screen = it[PreferencesKeys.SIGN_IN_STATE] ?: Screen.Intro.toString()

        when (screen) {
            Screen.Intro.toString() -> Screen.Intro
            Screen.Home.toString() -> Screen.Home
            else -> Screen.Intro
        }
    }.first()

    override suspend fun storeUser(user: LocalUser) {
        val jsonString = Json.encodeToString(user.toUserSerializable())

        ds.edit {
            it[PreferencesKeys.LOCAL_USER] = jsonString
        }
    }

    override suspend fun readUser(): LocalUser {
        val pref = ds.data.catch {
            emit(emptyPreferences())
        }.first()

        val response = pref[PreferencesKeys.LOCAL_USER]?.let {
            Json.decodeFromString<LocalUserSerializable>(it)
        }

        return response?.toLocalUser() ?: LocalUser()
    }
}