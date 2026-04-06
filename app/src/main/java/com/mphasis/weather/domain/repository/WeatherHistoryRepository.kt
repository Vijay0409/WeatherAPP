package com.mphasis.weather.domain.repository

import com.mphasis.weather.domain.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing weather history.
 * Provides methods to save and retrieve historical weather data.
 */
interface WeatherHistoryRepository {

    /**
     * Save a weather record to the history.
     */
    suspend fun saveWeatherHistory(weatherInfo: WeatherInfo)

    /**
     * Get all weather history records.
     */
    fun getAllWeatherHistory(): Flow<List<WeatherInfo>>

    /**
     * Get weather history for a specific city.
     */
    fun getWeatherHistoryByCity(cityName: String): Flow<List<WeatherInfo>>

    /**
     * Delete all weather history for a specific city.
     */
    suspend fun deleteHistoryByCity(cityName: String)

    /**
     * Delete all weather history.
     */
    suspend fun deleteAllHistory()

    /**
     * Get weather history within a specific time range.
     */
    fun getWeatherHistoryByTimeRange(startTime: Long, endTime: Long): Flow<List<WeatherInfo>>

    /**
     * Get the total count of history records.
     */
    fun getHistoryCount(): Flow<Int>
}

