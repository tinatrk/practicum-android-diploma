package ru.practicum.android.diploma.presentation.filters.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.util.common.Failure

@Immutable
sealed class CountryUiState {
    data object Loading : CountryUiState()
    data class Success(val data: List<FilterCountryUi>) : CountryUiState()
    data class Error(val failure: Failure) : CountryUiState()
}
