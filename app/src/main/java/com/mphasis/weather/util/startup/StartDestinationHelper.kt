package com.mphasis.weather.util.startup

import android.Manifest
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import com.mphasis.weather.data.local.PreferencesManager

/**
 * A composable that determines the starting destination of the app.
 *
 * It checks for a last-used city, requests location permission if necessary, and fetches the
 * current location to decide whether to start on the Weather screen or the Search screen.
 */
@Composable
fun rememberStartDestinationState(preferencesManager: PreferencesManager): StartDestinationState {
    val context = LocalContext.current
    var state by remember { mutableStateOf<StartDestinationState>(StartDestinationState.Loading) }
    val lastCity by preferencesManager.lastCheckedCity.collectAsState(initial = null)

    fun fetchLocationAndSetStart() {
        try {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                state = if (location != null) {
                    val city = _root_ide_package_.com.mphasis.weather.util.getCityFromLocation(
                        context,
                        location
                    )
                    StartDestinationState.Weather(city)
                } else {
                    StartDestinationState.Search
                }
            }.addOnFailureListener {
                state = StartDestinationState.Search
            }
        } catch (e: SecurityException) {
            state = StartDestinationState.Search
        }
    }

    val permissionLauncher =
        _root_ide_package_.com.mphasis.weather.util.rememberPermissionRequest { isGranted ->
            if (isGranted) {
                fetchLocationAndSetStart()
            } else {
                Toast.makeText(
                    context,
                    "Location permission denied. Please search for a city manually.",
                    Toast.LENGTH_LONG
                ).show()
                state = StartDestinationState.Search
            }
        }

    LaunchedEffect(lastCity) {
        // We only want to run this logic once, when the state is Loading.
        if (state !is StartDestinationState.Loading) return@LaunchedEffect

        if (!lastCity.isNullOrBlank()) {
            state = StartDestinationState.Weather(lastCity!!)
        } else {
            if (_root_ide_package_.com.mphasis.weather.util.isLocationPermissionGranted(context)) {
                fetchLocationAndSetStart()
            } else {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    return state
}
