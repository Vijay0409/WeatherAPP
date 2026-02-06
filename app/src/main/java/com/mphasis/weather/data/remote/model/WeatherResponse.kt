package com.mphasis.weather.data.remote.model

data class WeatherResponse(
    val cityName: String?,
    val weatherDescriptions: List<WeatherDescription> = emptyList(),
    val temperatureData: TemperatureData?,
    val wind: WindInfo?
)

data class WeatherDescription(
    val weatherDescription: String?,
    val weatherIcon: String?
)
data class WindInfo(
    val speed: Double?
)
data class TemperatureData(
    val temp: Double?,
    val feels_like: Double?,
    val temp_min: Double?,
    val temp_max: Double?,
    val humidity: Int?
)
