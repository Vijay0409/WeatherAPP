package com.mphasis.weather.domain.usecase

import com.mphasis.weather.domain.model.WeatherInfo
import com.mphasis.weather.domain.repository.WeatherHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting weather history.
 */
interface GetWeatherHistoryUseCase {
    operator fun invoke(): Flow<List<WeatherInfo>>
}

/**
 * Implementation of [GetWeatherHistoryUseCase].
 */
class GetWeatherHistoryUseCaseImpl @Inject constructor(
    private val weatherHistoryRepository: WeatherHistoryRepository
) : GetWeatherHistoryUseCase {
    override operator fun invoke(): Flow<List<WeatherInfo>> =
        weatherHistoryRepository.getAllWeatherHistory()
}

