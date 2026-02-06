package com.mphasis.weather.domain.repository

import com.mphasis.weather.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherByCity(city: String): WeatherInfo
}