package com.mphasis.weather.di

import com.mphasis.weather.data.repository.WeatherHistoryRepositoryImpl
import com.mphasis.weather.data.repository.WeatherRepositoryImpl
import com.mphasis.weather.domain.repository.WeatherHistoryRepository
import com.mphasis.weather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindWeatherHistoryRepository(
        impl: WeatherHistoryRepositoryImpl
    ): WeatherHistoryRepository
}