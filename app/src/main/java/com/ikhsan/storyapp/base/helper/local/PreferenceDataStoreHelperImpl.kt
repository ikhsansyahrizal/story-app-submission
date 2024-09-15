package com.ikhsan.storyapp.base.helper.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PreferenceDataStoreHelperImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): PreferenceDataStoreHelper {

    companion object {
        val USER_SESSION = stringPreferencesKey("user_session")
    }

    override suspend fun <T> savePreference(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): T {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences[key] ?: defaultValue
            }.first()
        }
    }

    override fun clearAllData() {
        runBlocking {
            dataStore.edit { data ->
                data.clear()
            }
        }
    }
}