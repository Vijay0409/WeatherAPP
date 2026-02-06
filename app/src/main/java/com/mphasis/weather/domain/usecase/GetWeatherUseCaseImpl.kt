package com.mphasis.weather.domain.usecase

import com.mphasis.weather.domain.model.WeatherInfo
import com.mphasis.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCaseImpl @Inject constructor(
    private val repository: WeatherRepository
) : GetWeatherUseCase {
    override suspend operator fun invoke(city: String): WeatherInfo =
        repository.getWeatherByCity(city)
}
