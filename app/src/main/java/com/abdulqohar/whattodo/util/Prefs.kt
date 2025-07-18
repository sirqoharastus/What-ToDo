package com.abdulqohar.whattodo.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object Prefs {
    private val Context.dataStore by preferencesDataStore("settings")
    val TOKEN_KEY = stringPreferencesKey("token")
    val USER_ID_KEY = intPreferencesKey("user_id")

    suspend fun saveAuth(context: Context, token: String, userId: Int) {
        context.dataStore.edit {
            it[TOKEN_KEY] = token
            it[USER_ID_KEY] = userId
        }
    }

    fun getToken(context: Context): Flow<String> = context.dataStore.data.map { it[TOKEN_KEY] ?: "" }

    fun getUserId(context: Context): Flow<Int> = context.dataStore.data.map { it[USER_ID_KEY] ?: 0 }

//    val tokenFlow = context.dataStore.data.map { it[TOKEN_KEY] ?: "" }
//    val userIdFlow = context.dataStore.data.map { it[USER_ID_KEY] ?: 0 }
}