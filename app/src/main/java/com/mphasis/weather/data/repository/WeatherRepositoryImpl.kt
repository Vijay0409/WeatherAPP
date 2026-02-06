package com.mphasis.weather.data.repository

import com.mphasis.weather.data.mapper.toDomain
import com.mphasis.weather.data.remote.WeatherApi
import com.mphasis.weather.domain.model.WeatherInfo
import com.mphasis.weather.domain.repository.WeatherRepository
import com.mphasis.weather.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherByCity(city: String): WeatherInfo {
        val response = api.getWeatherByCity(
            city = city,
            apiKey = Constants.API_KEY,
            units = Constants.UNITS
        )
        return response.toDomain()
    }
}
