package com.mphasis.weather.ui.weather

import com.mphasis.weather.domain.model.WeatherInfo
import com.mphasis.weather.domain.usecase.GetWeatherUseCase
import com.mphasis.weather.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: WeatherViewModel
    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getWeatherUseCase = mock()
        viewModel = WeatherViewModel(getWeatherUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadWeather success`() = runTest {
        val city = "abc"
        val weather = WeatherInfo(
            cityName = "abc",
            temperature = 15.0,
            feelsLike = 14.0,
            tempMin = 10.0,
            tempMax = 20.0,
            humidity = 80,
            windSpeed = 5.0,
            weatherDescription = "Clear sky",
            weatherIcon = "01d"
        )
        whenever(getWeatherUseCase(city)).thenReturn(weather)
        viewModel.loadWeather(city)
        advanceUntilIdle()
        val state = viewModel.weatherInfoState.first()
        assertEquals(UiState.Success(weather), state)
    }

    @Test
    fun `loadWeather error`() = runTest {
        val city = "abc"
        val errorMessage = "Error fetching weather"
        whenever(getWeatherUseCase(city)).thenThrow(RuntimeException(errorMessage))
        viewModel.loadWeather(city)
        advanceUntilIdle()
        val state = viewModel.weatherInfoState.first()
        assertEquals(UiState.Error(errorMessage), state)
    }
}
