package com.mphasis.weather.util

import android.content.Context
import android.location.Geocoder
import android.location.Location
import java.util.Locale

// Note: This uses a deprecated API. For production apps, consider the newer
// asynchronous Geocoder#getFromLocation method available on API 33+.
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
