package com.mphasis.weather.util.startup

sealed class StartDestinationState {
    object Loading : StartDestinationState()
    data class Weather(val city: String) : StartDestinationState()
    object Search : StartDestinationState()

}