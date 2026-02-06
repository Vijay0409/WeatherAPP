package com.mphasis.weather.data.mapper

import com.mphasis.weather.data.remote.model.WeatherResponse
import com.mphasis.weather.domain.model.WeatherInfo

/**
 * Converts the raw [WeatherResponse] from the API into a clean [WeatherInfo] domain model.
 *
 * This function safely handles nullable fields by providing default values, ensuring the
 * rest of the app works with reliable, non-nullable data.
 *
 * @return A [WeatherInfo] object suitable for use in the UI and business logic.
 */
fun WeatherResponse.toDomain(): WeatherInfo {
    val weatherDescription = weatherDescriptions.firstOrNull()
    return WeatherInfo(
        cityName = cityName.orEmpty(),
        temperature = temperatureData?.temp ?: 0.0,
        feelsLike = temperatureData?.feels_like ?: 0.0,
        tempMax = temperatureData?.temp_max ?: 0.0,
        tempMin = temperatureData?.temp_min ?: 0.0,
        humidity = temperatureData?.humidity ?: 0,
        windSpeed = wind?.speed ?: 0.0,
        weatherDescription = weatherDescription?.weatherDescription.orEmpty(),
        weatherIcon = weatherDescription?.weatherIcon.orEmpty()
    )
}