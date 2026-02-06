package com.mphasis.weather.di

import com.mphasis.weather.data.local.PreferencesManager
import com.mphasis.weather.data.local.PreferencesManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesManagerModule {

    @Binds
    abstract fun bindPreferencesManager(
        preferencesManagerImpl: PreferencesManagerImpl
    ): PreferencesManager
}