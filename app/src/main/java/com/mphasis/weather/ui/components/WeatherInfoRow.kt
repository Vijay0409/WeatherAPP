package com.mphasis.weather.ui.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.mphasis.weather.R
import com.mphasis.weather.domain.model.WeatherInfo
import com.mphasis.weather.ui.theme.*

@Composable
fun WeatherInfoDetailCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(cardPadding),
        elevation = CardDefaults.cardElevation(cardElevation)
    ) {
        Column(
            modifier = Modifier.padding(cardContentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = label, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(smallSpacerHeight))
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun MainWeatherCard(weatherInfoData: WeatherInfo, modifier: Modifier = Modifier) {
    Surface(
        shape = MaterialTheme.shapes.large,
        tonalElevation = cardElevation,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(screenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://openweathermap.org/img/wn/${weatherInfoData.weatherIcon}@4x.png",
                contentDescription = null,
                modifier = Modifier.size(weatherIconSize)
            )

            Text(
                text = stringResource(id = R.string.temperature_format, weatherInfoData.temperature.toInt()),
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                text = weatherInfoData.weatherDescription.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun WeatherDetailsGrid(weatherInfoData: WeatherInfo, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherInfoDetailCard(
                label = stringResource(R.string.high),
                value = stringResource(id = R.string.temperature_format, weatherInfoData.tempMax.toInt())
            )
            WeatherInfoDetailCard(
                label = stringResource(R.string.low),
                value = stringResource(id = R.string.temperature_format, weatherInfoData.tempMin.toInt())
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherInfoDetailCard(
                label = stringResource(R.string.humidity),
                value = stringResource(id = R.string.percentage_format, weatherInfoData.humidity)
            )
            WeatherInfoDetailCard(
                label = stringResource(R.string.wind),
                value = stringResource(id = R.string.wind_speed_format, weatherInfoData.windSpeed)
            )
        }
    }
}
