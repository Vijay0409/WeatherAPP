package com.mphasis.weather.navigation

sealed class Screen(val route: String) {
    data object Search : Screen("search")
    data object Weather : Screen("weather/{city}") {
        fun createRoute(city: String) = "weather/$city"
    }
}