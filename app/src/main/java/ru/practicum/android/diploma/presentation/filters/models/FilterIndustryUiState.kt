package ru.practicum.android.diploma.presentation.filters.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.util.common.Failure

@Immutable
sealed interface FilterIndustryUiState {
    data object Loading : FilterIndustryUiState
    data class Content(val data: List<FilterIndustryUi>, val curChoice: Int?) :
        FilterIndustryUiState

    data class Error(val error: Failure) : FilterIndustryUiState
}
