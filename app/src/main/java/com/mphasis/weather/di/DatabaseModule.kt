package com.mphasis.weather.di

import android.content.Context
import androidx.room.Room
import com.mphasis.weather.data.local.dao.WeatherHistoryDao
import com.mphasis.weather.data.local.database.WeatherHistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing Room database instances.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWeatherHistoryDatabase(
        @ApplicationContext context: Context
    ): WeatherHistoryDatabase =
        Room.databaseBuilder(
            context,
            WeatherHistoryDatabase::class.java,
            "weather_history_db"
        ).build()

    @Provides
    @Singleton
    fun provideWeatherHistoryDao(
        database: WeatherHistoryDatabase
    ): WeatherHistoryDao = database.weatherHistoryDao()
}

