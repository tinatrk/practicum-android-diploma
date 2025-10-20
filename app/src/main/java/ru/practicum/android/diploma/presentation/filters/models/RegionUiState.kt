package ru.practicum.android.diploma.presentation.filters.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.util.common.Failure

@Immutable
sealed interface RegionUiState {
    data object Loading : RegionUiState
    data class Success(val data: List<FilterRegionUi>) : RegionUiState
    data class Error(val failure: Failure) : RegionUiState
}
