package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterStorageInteractor
import ru.practicum.android.diploma.domain.models.filters.FilterAddress
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.domain.models.filters.FilterSettings
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys

class FilterSettingsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val filterInteractor: FilterStorageInteractor
) : ViewModel() {

    // Сделала так для простоты, по-хорошему нужен один state
    val selectedIndustry: StateFlow<FilterIndustry?> =
        savedStateHandle.getStateFlow(NavResultKeys.SELECTED_INDUSTRY, null)

    val selectedWorkAddress: StateFlow<FilterAddress?> =
        savedStateHandle.getStateFlow(NavResultKeys.SELECTED_WORK_ADDRESS, null)

    fun setFilterSettings() {
        viewModelScope.launch {
            filterInteractor.saveFilterSettings(
                FilterSettings(
                    address = FilterAddress(
                        country = FilterCountry(id = TEST_COUNTRY_ID, name = "")
                    ),
                    onlyWithSalary = true
                )
            )
        }
    }

    fun clearFilterSettings() {
        viewModelScope.launch {
            filterInteractor.clearFilterSettings()
        }
    }

    companion object {
        private const val TEST_COUNTRY_ID = 113
        private const val TEST_REGION_ID = 0
    }

}
