package com.mphasis.weather.data.remote.model

import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    @SerializedName("name")
    val cityName: String?,

    @SerializedName("weather")
    val weatherDescriptions: List<WeatherDescription>?,

    @SerializedName("main")
    val temperatureData: TemperatureData?,

    @SerializedName("wind")
    val wind: WindInfo?
)

data class WeatherDescription(
    @SerializedName("description")
    val weatherDescription: String?,
    @SerializedName("icon")
    val weatherIcon: String?
)

data class WindInfo(
    @SerializedName("speed")
    val speed: Double?
)

data class TemperatureData(
    @SerializedName("temp")
    val temp: Double?,

    @SerializedName("feels_like")
    val feels_like: Double?,

    @SerializedName("temp_min")
    val temp_min: Double?,

    @SerializedName("temp_max")
    val temp_max: Double?,

    @SerializedName("humidity")
    val humidity: Int?
)
