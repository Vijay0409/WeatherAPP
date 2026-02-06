package com.mphasis.weather.util

import android.content.Context
import android.location.Geocoder
import android.location.Location
import java.util.Locale

@Suppress("DEPRECATION")
fun getCityFromLocation(context: Context, location: Location): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val result = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        result?.firstOrNull()?.locality ?: "Unknown"
    } catch (e: Exception) {
        "Unknown"
    }
}
