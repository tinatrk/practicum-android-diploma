package ru.practicum.android.diploma.presentation.filters.models

import androidx.compose.runtime.Immutable

@Immutable
data class FilterSettingsUi(
    val address: FilterAddressUi? = null,
    val industry: FilterIndustryUi? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean? = null
)
