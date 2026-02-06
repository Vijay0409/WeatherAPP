package com.mphasis.weather.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("weather_prefs")

@Singleton
class PreferencesManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesManager {

    private companion object {
        val KEY_LAST_CHECKED_CITY = stringPreferencesKey("last_city")
    }

    override val lastCheckedCity: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_LAST_CHECKED_CITY]
    }

    override suspend fun setLastCheckedCity(city: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_LAST_CHECKED_CITY] = city
        }
    }
}
