package com.mphasis.weather.data.remote.model

data class WeatherResponse(
    val name: String?,
    val weather: List<WeatherDescription> = emptyList(),
    val main: MainInfo?,
    val wind: WindInfo?
)

data class WeatherDescription(
    val main: String?,
    val description: String?,
    val icon: String?
)
data class WindInfo(
    val speed: Double?
)
data class MainInfo(
    val temp: Double?,
    val feels_like: Double?,
    val temp_min: Double?,
    val temp_max: Double?,
    val humidity: Int?
)
