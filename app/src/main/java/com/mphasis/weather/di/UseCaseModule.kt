package com.mphasis.weather.di

import com.mphasis.weather.domain.usecase.GetWeatherUseCase
import com.mphasis.weather.domain.usecase.GetWeatherUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetWeatherUseCase(
        getWeatherUseCaseImpl: GetWeatherUseCaseImpl
    ): GetWeatherUseCase
}