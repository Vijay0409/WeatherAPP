package com.mphasis.weather.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mphasis.weather.data.local.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _cityState = MutableStateFlow("")
    val cityState: StateFlow<String> = _cityState

    init {
        viewModelScope.launch {
            val last = preferencesManager.lastCheckedCity.first()
            if (!last.isNullOrBlank()) {
                _cityState.value = last
            }
        }
    }

    fun onCityChange(value: String) {
        _cityState.value = value
    }

    fun onSearchConfirmed(onNavigate: (String) -> Unit) {
        val current = _cityState.value.trim()
        if (current.isNotEmpty()) {
            viewModelScope.launch {
                preferencesManager.setLastCheckedCity(current)
            }
            onNavigate(current)
        }
    }
}
