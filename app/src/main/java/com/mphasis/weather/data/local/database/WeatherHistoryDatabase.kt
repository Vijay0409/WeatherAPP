package com.mphasis.weather.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mphasis.weather.data.local.dao.WeatherHistoryDao
import com.mphasis.weather.data.local.entity.WeatherHistoryEntity

/**
 * Room database for storing weather history.
 * This database maintains a history of all weather queries made by the user.
 */
@Database(
    entities = [WeatherHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherHistoryDatabase : RoomDatabase() {
    abstract fun weatherHistoryDao(): WeatherHistoryDao
}

