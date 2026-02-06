package com.mphasis.weather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mphasis.weather.domain.model.WeatherInfo
import com.mphasis.weather.domain.usecase.GetWeatherUseCase
import com.mphasis.weather.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<WeatherInfo>>(UiState.Idle)
    val state: StateFlow<UiState<WeatherInfo>> = _state

    fun loadWeather(city: String) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val result = getWeatherUseCase(city)
                _state.value = UiState.Success(result)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
