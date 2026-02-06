package com.mphasis.weather.domain.usecase

import com.mphasis.weather.domain.model.WeatherInfo

interface GetWeatherUseCase {
    suspend operator fun invoke(city: String): WeatherInfo
}
