package com.mphasis.weather.ui.search

import com.mphasis.weather.data.local.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var preferencesManager: PreferencesManager

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        preferencesManager = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads last city`() = runTest {
        val lastCity = "New York"
        whenever(preferencesManager.lastCheckedCity).thenReturn(flowOf(lastCity))
        val viewModel = SearchViewModel(preferencesManager)
        advanceUntilIdle()
        assertEquals(lastCity, viewModel.cityState.value)
    }

    @Test
    fun `onCityChange updates city value`() = runTest {
        whenever(preferencesManager.lastCheckedCity).thenReturn(flowOf(""))
        val viewModel = SearchViewModel(preferencesManager)
        advanceUntilIdle()
        val newCity = "London"
        viewModel.onCityChange(newCity)
        assertEquals(newCity, viewModel.cityState.value)
    }

    @Test
    fun `onSearchConfirmed triggers navigation and saves city`() = runTest {
        whenever(preferencesManager.lastCheckedCity).thenReturn(flowOf(""))
        val viewModel = SearchViewModel(preferencesManager)
        advanceUntilIdle()
        val city = "Phoenix"
        viewModel.onCityChange(city)
        val onNavigateToWeather: (String) -> Unit = mock()
        viewModel.onSearchConfirmed(onNavigateToWeather)
        advanceUntilIdle()
        verify(onNavigateToWeather).invoke(city)
        verify(preferencesManager).setLastCheckedCity(city)
    }
}
