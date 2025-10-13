package ru.practicum.android.diploma.presentation.worklocation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class WorkLocationViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val countryData: StateFlow<String?> = savedStateHandle.getStateFlow(COUNTRY_KEY, null)
    val regionData: StateFlow<String?> = savedStateHandle.getStateFlow(REGION_KEY, null)

    /** При изменении значения на экране выбора страны значение региона будет сброшено */
    init {
        viewModelScope.launch {
            countryData
                .drop(1)
                .collect {
                    savedStateHandle[REGION_KEY] = null
                }
        }
    }
    fun clearWorkLocationData() {
        savedStateHandle[COUNTRY_KEY] = null
        savedStateHandle[REGION_KEY] = null
    }

    fun clearRegionData() {
        savedStateHandle[REGION_KEY] = null
    }

    companion object {
        const val COUNTRY_KEY = "COUNTRY"
        const val REGION_KEY = "REGION"
    }
}
