package com.mphasis.weather.ui.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mphasis.weather.R
import com.mphasis.weather.domain.model.WeatherInfo
import com.mphasis.weather.ui.theme.LightCyan
import com.mphasis.weather.ui.theme.SkyBlue
import com.mphasis.weather.ui.theme.screenPadding
import com.mphasis.weather.ui.theme.verticalSpacing
import com.mphasis.weather.util.UiState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    city: String,
    viewModel: WeatherViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.weatherInfoState.collectAsState()

    LaunchedEffect(city) {
        viewModel.loadWeather(city)
    }

    val title = when (val s = state) {
        is UiState.Success -> stringResource(
            id = R.string.weather_title_format,
            city,
            s.data.weatherDescription.replaceFirstChar { it.uppercase() })
        else -> city
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.loadWeather(city) }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = stringResource(R.string.refresh)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(SkyBlue, LightCyan)))
                .padding(paddingValues)
                .padding(screenPadding)
        ) {
            when (val s = state) {
                is UiState.Loading, is UiState.Idle -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = s.message,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                is UiState.Success -> {
                    // Main success UI is now in its own composable
                    WeatherSuccessContent(data = s.data)
                }
            }
        }
    }
}

@Composable
private fun WeatherSuccessContent(data: WeatherInfo) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(verticalSpacing)
    ) {
        val dateFormat = stringResource(id = R.string.date_format)
        val currentDate = remember {
            LocalDate.now().format(DateTimeFormatter.ofPattern(dateFormat))
        }

        Text(
            text = currentDate,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        // Using the new, reusable components
        MainWeatherCard(weatherInfoData = data, modifier = Modifier.fillMaxWidth())
        WeatherDetailsGrid(data = data)
    }
}
