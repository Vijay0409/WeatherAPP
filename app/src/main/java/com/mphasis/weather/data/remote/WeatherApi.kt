package com.mphasis.weather.data.remote

import com.mphasis.weather.data.remote.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q")
        city: String,
        @Query("appid")
        apiKey: String,
        @Query("units")
        units: String
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat")
        lat: Double,
        @Query("lon")
        lon: Double,
        @Query("appid")
        apiKey: String,
        @Query("units")
        units: String
    ): WeatherResponse

}