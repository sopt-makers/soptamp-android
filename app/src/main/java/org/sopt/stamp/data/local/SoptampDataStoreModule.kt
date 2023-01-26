package org.sopt.stamp.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class SoptampDataStoreModule(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "pref")

    /** USER_ID  */
    private val USER_ID = intPreferencesKey("user_id")
    val userId: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[USER_ID] ?: -1
        }

    suspend fun setUserId(userId: Int) {
        context.dataStore.edit { pref ->
            pref[USER_ID] = userId
        }
    }

    /** USER_ID  */
    private val PROFILE_MESSAGE = stringPreferencesKey("profile_message")
    val profileMessage: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PROFILE_MESSAGE] ?: "String"
        }

    suspend fun setProfileMessage(profileMessage: String) {
        context.dataStore.edit { pref ->
            pref[PROFILE_MESSAGE] = profileMessage
        }
    }

}