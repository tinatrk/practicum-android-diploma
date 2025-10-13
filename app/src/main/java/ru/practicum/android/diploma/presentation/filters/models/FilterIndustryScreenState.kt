package ru.practicum.android.diploma.presentation.filters.models

import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.util.common.Failure

sealed interface FilterIndustryScreenState {
    data object Loading : FilterIndustryScreenState
    data class Content(val data: List<FilterIndustry>, val curChoice: Int?) : FilterIndustryScreenState
    data class Error(val error: Failure) : FilterIndustryScreenState
}
