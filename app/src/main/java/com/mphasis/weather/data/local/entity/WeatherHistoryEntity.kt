package com.mphasis.weather.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a weather record in the local database.
 * Each record stores weather information for a specific city at a specific time.
 */
@Entity(tableName = "weather_history")
data class WeatherHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityName: String,
    val temperature: Double,
    val feelsLike: Double,
    val tempMax: Double,
    val tempMin: Double,
    val humidity: Int,
    val windSpeed: Double,
    val weatherDescription: String,
    val weatherIcon: String,
    val timestamp: Long = System.currentTimeMillis()
)

