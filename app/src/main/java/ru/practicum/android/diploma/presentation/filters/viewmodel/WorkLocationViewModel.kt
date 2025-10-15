package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.models.filters.FilterAddress
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterRegion
import ru.practicum.android.diploma.presentation.filters.models.WorkLocationNavEvent
import ru.practicum.android.diploma.presentation.filters.models.WorkLocationUiState
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys
import ru.practicum.android.diploma.util.common.Resource

class WorkLocationViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val filterInteractor: FilterInteractor
) : ViewModel() {
    private var countryData: StateFlow<FilterCountry?> =
        savedStateHandle.getStateFlow(NavResultKeys.SELECTED_COUNTRY, null)
    private val regionData: StateFlow<FilterRegion?> =
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

    private val _workLocationNavEvent =
        MutableSharedFlow<WorkLocationNavEvent>(extraBufferCapacity = 1)
    val workLocationNavEvent: SharedFlow<WorkLocationNavEvent> = _workLocationNavEvent
    private val _workLocationToastEvent = Channel<Unit>(capacity = Channel.BUFFERED)
    val workLocationToastEvent = _workLocationToastEvent.receiveAsFlow()

    /** При изменении значения страны значение региона будет сброшено, если регион не соответствует
     * этой стране */
    init {
        viewModelScope.launch {
            countryData
                .drop(1)
                .collect {
                    if (it?.id != regionData.value?.parentId) {
                        savedStateHandle[NavResultKeys.SELECTED_REGION] = null
                    }
                }
        }
        regionData
            .onEach {
                if (it?.parentId != null && it.parentId != countryData.value?.id) {
                    updateCountryName(it.parentId)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun updateCountryName(countryId: Int?) {
        if (countryId == null) return
        if (countryId == countryData.value?.id) return
        viewModelScope.launch {
            filterInteractor.getCountryNameById(countryId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        savedStateHandle[NavResultKeys.SELECTED_COUNTRY] = FilterCountry(
                            id = countryId,
                            name = result.data
                        )
                    }

                    else -> {
                        clearWorkLocationData()
                        _workLocationToastEvent.send(Unit)
                    }
                }

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
        if (countryData.value != null) {
            savedStateHandle[NavResultKeys.SELECTED_WORK_ADDRESS] =
                FilterAddress(
                    country = countryData.value!!,
                    region = regionData.value
                )
        } else {
            savedStateHandle[NavResultKeys.SELECTED_WORK_ADDRESS] = null
        }
        clearWorkLocationData()

        viewModelScope.launch {
            _workLocationNavEvent.emit(WorkLocationNavEvent.NavigateBack)
        }
    }

    companion object {
        private const val STOP_TIMEOUT_MILLIS = 5000L
    }
}
