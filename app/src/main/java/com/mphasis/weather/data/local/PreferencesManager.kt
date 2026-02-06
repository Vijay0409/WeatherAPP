package com.mphasis.weather.data.local

import kotlinx.coroutines.flow.Flow

interface PreferencesManager {
    val lastCheckedCity: Flow<String?>
    suspend fun setLastCheckedCity(city: String)
}
