package ru.practicum.android.diploma.presentation.worklocation.models

import ru.practicum.android.diploma.domain.models.filters.FilterCountry
import ru.practicum.android.diploma.domain.models.filters.FilterRegion

data class WorkLocationUiState(
    val country: FilterCountry? = null,
    val region: FilterRegion? = null
)
