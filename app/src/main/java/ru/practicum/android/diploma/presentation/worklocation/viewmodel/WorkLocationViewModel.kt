package ru.practicum.android.diploma.presentation.worklocation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.presentation.worklocation.models.WorkLocationNavEvent
import ru.practicum.android.diploma.presentation.worklocation.models.WorkLocationUiState
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys

private const val STOP_TIMEOUT_MILLIS: Long = 5_000L

class WorkLocationViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val countryData: StateFlow<FilterCountry?> =
        savedStateHandle.getStateFlow(NavResultKeys.SELECTED_COUNTRY, null)
    private val regionData: StateFlow<FilterIndustry?> =
        savedStateHandle.getStateFlow(NavResultKeys.SELECTED_REGION, null)

    /** Общий state для страны и региона. Реагирует на изменение любого из компонентов */
    val workLocationUiState: StateFlow<WorkLocationUiState> =
        combine(
            countryData,
            regionData
        ) { county, region ->
            WorkLocationUiState(county, region)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            WorkLocationUiState()
        )

    private val _workLocationNavEvent = MutableSharedFlow<WorkLocationNavEvent>(extraBufferCapacity = 1)
    val workLocationNavEvent: SharedFlow<WorkLocationNavEvent> = _workLocationNavEvent

    /** При изменении значения страны значение региона будет сброшено */
    init {
        viewModelScope.launch {
            countryData
                .drop(1)
                .collect {
                    savedStateHandle[NavResultKeys.SELECTED_REGION] = null
                }
        }
    }
    fun clearWorkLocationData() {
        savedStateHandle[NavResultKeys.SELECTED_COUNTRY] = null
        savedStateHandle[NavResultKeys.SELECTED_REGION] = null
    }

    fun clearRegionData() {
        savedStateHandle[NavResultKeys.SELECTED_REGION] = null
    }

    fun navigateToCountryScreen() {
        viewModelScope.launch {
            _workLocationNavEvent.emit(
                WorkLocationNavEvent.NavigateToCountryScreen
            )
        }
    }

    fun navigateToRegionScreen() {
        viewModelScope.launch {
            _workLocationNavEvent.emit(
                WorkLocationNavEvent.NavigateToRegionScreen(
                    countryData.value?.id
                )
            )
        }
    }

    fun finishWithResult() {
        savedStateHandle[NavResultKeys.SELECTED_COUNTRY] = countryData.value
        savedStateHandle[NavResultKeys.SELECTED_REGION] = regionData.value

        viewModelScope.launch {
            _workLocationNavEvent.emit(WorkLocationNavEvent.NavigateBack)
        }
    }
}
