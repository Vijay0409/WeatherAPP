package com.mphasis.weather.data.local

import kotlinx.coroutines.flow.Flow

interface PreferencesManager {
    val lastCity: Flow<String?>
    suspend fun setLastCity(city: String)
}
