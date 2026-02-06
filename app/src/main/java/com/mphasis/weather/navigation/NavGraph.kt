package com.mphasis.weather.navigation


import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.mphasis.weather.data.local.PreferencesManager
import com.mphasis.weather.ui.search.SearchScreen
import com.mphasis.weather.ui.search.SearchViewModel
import com.mphasis.weather.ui.weather.WeatherScreen
import com.mphasis.weather.ui.weather.WeatherViewModel
import com.mphasis.weather.util.startup.*

@Composable
fun WeatherNavGraph(
    navController: NavHostController,
    preferencesManager: PreferencesManager
) {
    val startDestinationState = rememberStartDestinationState(preferencesManager)

    when (startDestinationState) {
        is StartDestinationState.Loading -> {
            // While the destination is being determined, show a loading indicator.
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is StartDestinationState.Search -> {
            // The starting destination is the Search screen.
            WeatherNavHost(navController, Screen.Search.route)
        }
        is StartDestinationState.Weather -> {
            // The starting destination is the Weather screen for a specific city.
            val startRoute = Screen.Weather.createRoute(startDestinationState.city)
            WeatherNavHost(navController, startRoute)
        }
    }
}

@Composable
private fun WeatherNavHost(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Search.route) {
            val vm: SearchViewModel = hiltViewModel()
            SearchScreen(
                viewModel = vm,
                onNavigateToWeather = { city ->
                    navController.navigate(Screen.Weather.createRoute(city))
                }
            )
        }

        composable(Screen.Weather.route) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city").orEmpty()
            val vm: WeatherViewModel = hiltViewModel()

            WeatherScreen(
                city = city,
                viewModel = vm,
                onBack = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Screen.Search.route) {
                            popUpTo(Screen.Search.route) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}
