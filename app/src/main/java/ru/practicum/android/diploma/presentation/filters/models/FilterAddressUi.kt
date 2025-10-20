package ru.practicum.android.diploma.presentation.filters.models

import androidx.compose.runtime.Immutable

@Immutable
data class FilterAddressUi(
    val country: FilterCountryUi,
    val region: FilterRegionUi? = null
)
