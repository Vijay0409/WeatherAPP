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

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city

    init {
        viewModelScope.launch {
            val last = preferencesManager.lastCity.first()
            if (!last.isNullOrBlank()) {
                _city.value = last
            }
        }
    }

    fun onCityChange(value: String) {
        _city.value = value
    }

    fun onSearchConfirmed(onNavigate: (String) -> Unit) {
        val current = _city.value.trim()
        if (current.isNotEmpty()) {
            viewModelScope.launch {
                preferencesManager.setLastCity(current)
            }
            onNavigate(current)
        }
    }
}
