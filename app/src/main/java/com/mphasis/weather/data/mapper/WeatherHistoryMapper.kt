package com.mphasis.weather.data.mapper

import com.mphasis.weather.data.local.entity.WeatherHistoryEntity
import com.mphasis.weather.domain.model.WeatherInfo

/**
 * Converts [WeatherInfo] domain model to [WeatherHistoryEntity] for database storage.
 */
fun WeatherInfo.toWeatherHistoryEntity(): WeatherHistoryEntity {
    return WeatherHistoryEntity(
        cityName = this.cityName,
        temperature = this.temperature,
        feelsLike = this.feelsLike,
        tempMax = this.tempMax,
        tempMin = this.tempMin,
        humidity = this.humidity,
        windSpeed = this.windSpeed,
        weatherDescription = this.weatherDescription,
        weatherIcon = this.weatherIcon,
        timestamp = System.currentTimeMillis()
    )
}

/**
 * Converts [WeatherHistoryEntity] from database to [WeatherInfo] domain model.
 */
fun WeatherHistoryEntity.toDomain(): WeatherInfo {
    return WeatherInfo(
        cityName = this.cityName,
        temperature = this.temperature,
        feelsLike = this.feelsLike,
        tempMax = this.tempMax,
        tempMin = this.tempMin,
        humidity = this.humidity,
        windSpeed = this.windSpeed,
        weatherDescription = this.weatherDescription,
        weatherIcon = this.weatherIcon
    )
}

