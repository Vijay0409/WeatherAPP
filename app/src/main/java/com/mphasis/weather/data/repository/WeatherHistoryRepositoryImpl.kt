package com.mphasis.weather.data.repository

import com.mphasis.weather.data.local.dao.WeatherHistoryDao
import com.mphasis.weather.data.mapper.toDomain
import com.mphasis.weather.data.mapper.toWeatherHistoryEntity
import com.mphasis.weather.domain.model.WeatherInfo
import com.mphasis.weather.domain.repository.WeatherHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [WeatherHistoryRepository] that interacts with the Room database.
 */
@Singleton
class WeatherHistoryRepositoryImpl @Inject constructor(
    private val weatherHistoryDao: WeatherHistoryDao
) : WeatherHistoryRepository {

    override suspend fun saveWeatherHistory(weatherInfo: WeatherInfo) {
        weatherHistoryDao.insertWeatherHistory(weatherInfo.toWeatherHistoryEntity())
    }

    override fun getAllWeatherHistory(): Flow<List<WeatherInfo>> {
        return weatherHistoryDao.getAllWeatherHistory().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getWeatherHistoryByCity(cityName: String): Flow<List<WeatherInfo>> {
        return weatherHistoryDao.getWeatherHistoryByCity(cityName).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun deleteHistoryByCity(cityName: String) {
        weatherHistoryDao.deleteHistoryByCity(cityName)
    }

    override suspend fun deleteAllHistory() {
        weatherHistoryDao.deleteAllHistory()
    }

    override fun getWeatherHistoryByTimeRange(startTime: Long, endTime: Long): Flow<List<WeatherInfo>> {
        return weatherHistoryDao.getWeatherHistoryByTimeRange(startTime, endTime).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getHistoryCount(): Flow<Int> {
        return weatherHistoryDao.getHistoryCount()
    }
}

