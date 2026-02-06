package com.mphasis.weather.domain.model

data class WeatherInfo(
    val cityName: String,
    val temperature: Double,
    val feelsLike: Double,
    val tempMax: Double,
    val tempMin: Double,
    val humidity: Int,
    val windSpeed: Double,
    val weatherDescription: String,
    val weatherIcon: String
)
