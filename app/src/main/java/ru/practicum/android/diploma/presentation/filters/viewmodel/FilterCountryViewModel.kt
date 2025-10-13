package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys

class FilterCountryViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _shouldFinish = MutableStateFlow(false)
    val shouldFinish: StateFlow<Boolean> = _shouldFinish

    fun onReturnWithParam() {
        savedStateHandle[NavResultKeys.SELECTED_COUNTRY] =
            FilterCountry(id = 1, name = "Россия")
        _shouldFinish.value = true
    }

    fun onReturnWithoutParam() {
        savedStateHandle[NavResultKeys.SELECTED_COUNTRY] = null
        _shouldFinish.value = true
    }

    fun consumeFinishEvent() {
        _shouldFinish.value = false
    }
}
