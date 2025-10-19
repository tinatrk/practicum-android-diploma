package ru.practicum.android.diploma.presentation.filters.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.api.interactor.FilterInteractor
import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.presentation.filters.models.CountryUiState
import ru.practicum.android.diploma.ui.navigation.util.NavResultKeys
import ru.practicum.android.diploma.util.common.Resource

class FilterCountryViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: FilterInteractor
) : ViewModel() {
    private val _shouldFinish = MutableStateFlow(false)
    val shouldFinish: StateFlow<Boolean> = _shouldFinish

    private val _countryUiState = MutableStateFlow<CountryUiState>(CountryUiState.Loading)
    val countryUiState = _countryUiState.asStateFlow()

    init {
        loadCounties()
    }

    private fun loadCounties() {
        viewModelScope.launch {
            _countryUiState.value = CountryUiState.Loading
            interactor.getCountries()
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _countryUiState.value = CountryUiState.Success(resource.data)
                        }

                        is Resource.Error -> {
                            _countryUiState.value = CountryUiState.Error(resource.error)
                        }
                    }
                }
        }
    }

    fun onReturnWithParam(id: Int, name: String) {
        savedStateHandle[NavResultKeys.SELECTED_COUNTRY] =
            FilterCountry(id = id, name = name)
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
