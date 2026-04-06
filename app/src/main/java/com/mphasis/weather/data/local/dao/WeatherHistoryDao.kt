package com.mphasis.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mphasis.weather.data.local.entity.WeatherHistoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for weather history operations.
 * Provides methods to interact with the weather_history table.
 */
@Dao
interface WeatherHistoryDao {

    /**
     * Insert a new weather history record.
     */
    @Insert
    suspend fun insertWeatherHistory(entity: WeatherHistoryEntity)

    /**
     * Get all weather history records as a Flow for reactive updates.
     */
    @Query("SELECT * FROM weather_history ORDER BY timestamp DESC")
    fun getAllWeatherHistory(): Flow<List<WeatherHistoryEntity>>

    /**
     * Get weather history for a specific city.
     */
    @Query("SELECT * FROM weather_history WHERE cityName = :cityName ORDER BY timestamp DESC")
    fun getWeatherHistoryByCity(cityName: String): Flow<List<WeatherHistoryEntity>>

    /**
     * Get the latest weather record for a specific city.
     */
    @Query("SELECT * FROM weather_history WHERE cityName = :cityName ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestWeatherByCity(cityName: String): WeatherHistoryEntity?

    /**
     * Delete a specific weather history record.
     */
    @Delete
    suspend fun deleteWeatherHistory(entity: WeatherHistoryEntity)

    /**
     * Delete all weather history records for a specific city.
     */
    @Query("DELETE FROM weather_history WHERE cityName = :cityName")
    suspend fun deleteHistoryByCity(cityName: String)

    /**
     * Delete all weather history records.
     */
    @Query("DELETE FROM weather_history")
    suspend fun deleteAllHistory()

    /**
     * Get weather history records within a specific time range.
     */
    @Query("SELECT * FROM weather_history WHERE timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    fun getWeatherHistoryByTimeRange(startTime: Long, endTime: Long): Flow<List<WeatherHistoryEntity>>

    /**
     * Get the count of records in weather history.
     */
    @Query("SELECT COUNT(*) FROM weather_history")
    fun getHistoryCount(): Flow<Int>
}

