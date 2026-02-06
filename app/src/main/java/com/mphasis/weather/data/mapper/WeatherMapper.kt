package com.mphasis.weather.data.mapper

import com.mphasis.weather.data.remote.model.WeatherResponse
import com.mphasis.weather.domain.model.WeatherInfo

fun WeatherResponse.toDomain(): WeatherInfo {
    val first = weather.firstOrNull()
    return WeatherInfo(
        city = name.orEmpty(),
        temperature = main?.temp ?: 0.0,
        feelsLike = main?.feels_like ?: 0.0,
        tempMax = main?.temp_max ?: 0.0,
        tempMin = main?.temp_min ?: 0.0,
        humidity = main?.humidity ?: 0,
        windSpeed = wind?.speed ?: 0.0,
        description = first?.description.orEmpty(),
        icon = first?.icon.orEmpty()
    )
}