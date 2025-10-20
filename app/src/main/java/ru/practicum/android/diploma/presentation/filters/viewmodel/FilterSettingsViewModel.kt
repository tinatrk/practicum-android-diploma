package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterStorageInteractor
import ru.practicum.android.diploma.domain.models.filters.FilterAddress
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.domain.models.filters.FilterSettings
import ru.practicum.android.diploma.presentation.filters.models.FilterSettingsEvent
import ru.practicum.android.diploma.presentation.filters.models.FilterSettingsUiState
import ru.practicum.android.diploma.presentation.mappers.FilterConverter
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys

class FilterSettingsViewModel(
    private val filterStorageInteractor: FilterStorageInteractor,
    private val savedStateHandle: SavedStateHandle,
    private val converter: FilterConverter
) : ViewModel() {

    private val _events = MutableSharedFlow<FilterSettingsEvent>()
    val events: SharedFlow<FilterSettingsEvent> = _events

    private val navIndustry: StateFlow<FilterIndustry?> =
        savedStateHandle.getStateFlow(NavResultKeys.SELECTED_INDUSTRY, null)

    private val navAddress: StateFlow<FilterAddress?> =
        savedStateHandle.getStateFlow(NavResultKeys.SELECTED_WORK_ADDRESS, null)

    private val _screenState = MutableStateFlow(FilterSettingsUiState())
    val screenState: StateFlow<FilterSettingsUiState> = _screenState

    init {
        viewModelScope.launch {
            filterStorageInteractor.getFilterSettings().collect { stored ->
                if (stored != null) {
                    applyFromStorage(stored)
                } else {
                    _screenState.update { FilterSettingsUiState(initial = null) }
                }
            }
        }

        viewModelScope.launch {
            combine(
                navIndustry,
                navAddress
            ) { industry, address -> industry to address }.collect { (industry, address) ->
                _screenState.update {
                    val newAddress = address?.let {
                        converter.toFilterAddressUi(it)
                    } ?: it.address
                    val newIndustry = industry?.let {
                        converter.toFilterIndustryUi(it)
                    } ?: it.industry

                    if (newAddress == it.address && newIndustry == it.industry) {
                        it
                    } else {
                        it.copy(
                            address = newAddress,
                            industry = newIndustry
                        )
                    }
                }
            }
        }
    }

    private fun applyFromStorage(storage: FilterSettings) {
        _screenState.update { state ->
            state.copy(
                address = storage.address?.let {
                    converter.toFilterAddressUi(it)
                },
                industry = storage.industry?.let {
                    converter.toFilterIndustryUi(it)
                },
                salary = storage.salary?.toString().orEmpty(),
                onlyWithSalary = storage.onlyWithSalary ?: false,
                initial = converter.toFilterSettingsUi(storage)
            )
        }
    }

    fun onSalaryChange(salary: String) {
        _screenState.update { it.copy(salary = salary) }
    }

    fun onShowWithoutSalaryChange(showWithoutSalary: Boolean) {
        _screenState.update { it.copy(onlyWithSalary = showWithoutSalary) }
    }

    fun onClearAddress() {
        _screenState.update { it.copy(address = null) }
        savedStateHandle[NavResultKeys.SELECTED_WORK_ADDRESS] = null
    }

    fun onClearIndustry() {
        _screenState.update { it.copy(industry = null) }
        savedStateHandle[NavResultKeys.SELECTED_INDUSTRY] = null
    }

    fun onApply() {
        val filterSettings = FilterSettings(
            address = _screenState.value.address?.let {
                converter.toFilterAddress(it)
            },
            industry = _screenState.value.industry?.let {
                converter.toFilterIndustry(it)
            },
            salary = _screenState.value.salary.toIntOrNull(),
            onlyWithSalary = _screenState.value.onlyWithSalary
        )
        filterStorageInteractor.saveFilterSettings(filterSettings)
        _screenState.update { it.copy(initial = converter.toFilterSettingsUi(filterSettings)) }
        viewModelScope.launch {
            _events.emit(FilterSettingsEvent.NavigateBack)
        }
    }

    fun onReset() {
        filterStorageInteractor.clearFilterSettings()
        _screenState.update { FilterSettingsUiState(initial = null) }
        savedStateHandle[NavResultKeys.SELECTED_WORK_ADDRESS] = null
        savedStateHandle[NavResultKeys.SELECTED_INDUSTRY] = null
        viewModelScope.launch {
            _events.emit(FilterSettingsEvent.NavigateBack)
        }
    }

    fun navigateToWorkLocation() {
        savedStateHandle[NavResultKeys.SELECTED_COUNTRY] = _screenState.value.address?.country?.let {
            converter.toFilterCountry(it)
        }

        savedStateHandle[NavResultKeys.SELECTED_REGION] = _screenState.value.address?.region?.let {
            converter.toFilterRegion(it)
        }

        viewModelScope.launch {
            _events.emit(FilterSettingsEvent.NavigateToWorkLocation)
        }
    }
}
