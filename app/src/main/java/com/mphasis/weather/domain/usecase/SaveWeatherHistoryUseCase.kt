package com.mphasis.weather.domain.usecase

import com.mphasis.weather.domain.model.WeatherInfo
import com.mphasis.weather.domain.repository.WeatherHistoryRepository
import javax.inject.Inject

/**
 * Use case for saving weather data to history.
 */
interface SaveWeatherHistoryUseCase {
    suspend operator fun invoke(weatherInfo: WeatherInfo)
}

/**
 * Implementation of [SaveWeatherHistoryUseCase].
 */
class SaveWeatherHistoryUseCaseImpl @Inject constructor(
    private val weatherHistoryRepository: WeatherHistoryRepository
) : SaveWeatherHistoryUseCase {
    override suspend operator fun invoke(weatherInfo: WeatherInfo) {
        weatherHistoryRepository.saveWeatherHistory(weatherInfo)
    }
}

