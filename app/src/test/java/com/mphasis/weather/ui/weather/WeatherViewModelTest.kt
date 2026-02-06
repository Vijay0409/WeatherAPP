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
        val city = "London"
        val weather = WeatherInfo(
            city = "London",
            temperature = 15.0,
            feelsLike = 14.0,
            tempMin = 10.0,
            tempMax = 20.0,
            humidity = 80,
            windSpeed = 5.0,
            description = "Clear sky",
            icon = "01d"
        )
        whenever(getWeatherUseCase(city)).thenReturn(weather)
        viewModel.loadWeather(city)
        advanceUntilIdle()
        val state = viewModel.state.first()
        assertEquals(UiState.Success(weather), state)
    }

    @Test
    fun `loadWeather error`() = runTest {
        val city = "London"
        val errorMessage = "Error fetching weather"
        whenever(getWeatherUseCase(city)).thenThrow(RuntimeException(errorMessage))
        viewModel.loadWeather(city)
        advanceUntilIdle()
        val state = viewModel.state.first()
        assertEquals(UiState.Error(errorMessage), state)
    }
}
