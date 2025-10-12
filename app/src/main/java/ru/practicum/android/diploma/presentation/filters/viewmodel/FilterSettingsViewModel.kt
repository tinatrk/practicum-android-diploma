package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys

class FilterSettingsViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val selectedIndustry: StateFlow<FilterIndustry?> =
        savedStateHandle.getStateFlow(NavResultKeys.SELECTED_INDUSTRY, null)
}
