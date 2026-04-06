package com.mphasis.weather.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mphasis.weather.domain.model.WeatherInfo
import com.mphasis.weather.domain.usecase.GetWeatherHistoryUseCase
import com.mphasis.weather.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the weather history screen.
 * Manages the state and business logic for displaying weather history.
 */
@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(
    private val getWeatherHistoryUseCase: GetWeatherHistoryUseCase
) : ViewModel() {

    private val _weatherHistoryState = MutableStateFlow<UiState<List<WeatherInfo>>>(UiState.Idle)
    val weatherHistoryState: StateFlow<UiState<List<WeatherInfo>>> = _weatherHistoryState.asStateFlow()

    init {
        loadWeatherHistory()
    }

    /**
     * Load all weather history records.
     */
    fun loadWeatherHistory() {
        viewModelScope.launch {
            _weatherHistoryState.value = UiState.Loading
            try {
                getWeatherHistoryUseCase().collect { weatherList ->
                    _weatherHistoryState.value = UiState.Success(weatherList)
                }
            } catch (e: Exception) {
                _weatherHistoryState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

