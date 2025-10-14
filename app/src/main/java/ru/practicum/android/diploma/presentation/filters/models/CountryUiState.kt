package ru.practicum.android.diploma.presentation.filters.models

import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.util.common.Failure

sealed class CountryUiState {
    data object Loading : CountryUiState()
    data class Success(val data: List<FilterCountry>) : CountryUiState()
    data class Error(val failure: Failure) : CountryUiState()
}
