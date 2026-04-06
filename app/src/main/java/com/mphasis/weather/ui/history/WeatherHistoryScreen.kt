package com.mphasis.weather.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mphasis.weather.R
import com.mphasis.weather.domain.model.WeatherInfo
import com.mphasis.weather.ui.theme.*
import com.mphasis.weather.util.UiState
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHistoryScreen(
    viewModel: WeatherHistoryViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val historyState by viewModel.weatherHistoryState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Weather History") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.loadWeatherHistory() }) {
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
        ) {
            when (val state = historyState) {
                is UiState.Loading, is UiState.Idle -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                is UiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(verticalSpacing)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = Color.White
                            )
                            Text(
                                text = state.message,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(screenPadding)
                            )
                        }
                    }
                }
                is UiState.Success -> {
                    if (state.data.isEmpty()) {
                        EmptyHistoryContent()
                    } else {
                        HistoryListContent(weatherList = state.data)
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyHistoryContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(verticalSpacing),
            modifier = Modifier.padding(screenPadding)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = Color.White.copy(alpha = 0.6f)
            )
            Text(
                text = "No Weather History",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Search for a city to see its weather history",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun HistoryListContent(weatherList: List<WeatherInfo>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(screenPadding),
        verticalArrangement = Arrangement.spacedBy(verticalSpacing)
    ) {
        item {
            Text(
                text = "Total Records: ${weatherList.size}",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White.copy(alpha = 0.9f),
                fontWeight = FontWeight.SemiBold
            )
        }

        items(weatherList) { weather ->
            WeatherHistoryCard(weatherInfo = weather)
        }

        item {
            Spacer(modifier = Modifier.height(verticalSpacing))
        }
    }
}

@Composable
private fun WeatherHistoryCard(weatherInfo: WeatherInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(cardContentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left side - Weather info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // City name
                Text(
                    text = weatherInfo.cityName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Current temperature
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${weatherInfo.temperature.toInt()}°",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1976D2)
                    )
                    Text(
                        text = weatherInfo.weatherDescription.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                // Humidity and Wind
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WeatherDetailChip(
                        label = "Humidity",
                        value = "${weatherInfo.humidity}%",
                        modifier = Modifier.weight(1f)
                    )
                    WeatherDetailChip(
                        label = "Wind",
                        value = "${weatherInfo.windSpeed.toInt()} m/s",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Right side - Temperature range
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TemperatureGauge(
                    maxTemp = weatherInfo.tempMax,
                    minTemp = weatherInfo.tempMin,
                    currentTemp = weatherInfo.temperature
                )
            }
        }
    }
}

@Composable
private fun WeatherDetailChip(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            fontSize = MaterialTheme.typography.labelSmall.fontSize * 0.85f
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
private fun TemperatureGauge(
    maxTemp: Double,
    minTemp: Double,
    currentTemp: Double
) {
    val normalizedCurrent = when {
        maxTemp == minTemp -> 0.5f
        else -> ((currentTemp - minTemp) / (maxTemp - minTemp)).coerceIn(0.0, 0.0).toFloat()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = maxTemp.toInt().toString(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2)
        )

        // Temperature bar
        Box(
            modifier = Modifier
                .width(6.dp)
                .height(60.dp)
                .background(
                    color = Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(3.dp)
                )
        ) {
            // Current temperature indicator
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight(1f - normalizedCurrent)
                    .background(
                        color = Color(0xFF1976D2),
                        shape = RoundedCornerShape(3.dp)
                    )
                    .align(Alignment.BottomCenter)
            )
        }

        Text(
            text = minTemp.toInt().toString(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0288D1)
        )
    }
}

