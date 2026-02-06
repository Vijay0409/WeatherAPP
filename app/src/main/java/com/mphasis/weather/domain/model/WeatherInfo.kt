package com.mphasis.weather.domain.model

data class WeatherInfo(
    val city: String,
    val temperature: Double,
    val feelsLike: Double,
    val tempMax: Double,
    val tempMin: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val icon: String
)
