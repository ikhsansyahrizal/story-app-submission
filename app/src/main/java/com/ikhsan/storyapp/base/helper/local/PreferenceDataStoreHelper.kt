package com.ikhsan.storyapp.base.helper.local

import androidx.datastore.preferences.core.Preferences

interface PreferenceDataStoreHelper {

    suspend fun <T> savePreference(key: Preferences.Key<T>, value: T)

    fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): T

}