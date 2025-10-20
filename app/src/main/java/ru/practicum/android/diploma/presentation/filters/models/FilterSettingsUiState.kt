package ru.practicum.android.diploma.presentation.filters.models

import androidx.compose.runtime.Immutable

@Immutable
data class FilterSettingsUiState(
    val address: FilterAddressUi? = null,
    val industry: FilterIndustryUi? = null,
    val salary: String = "",
    val onlyWithoutSalary: Boolean = false,
    val initial: FilterSettingsUi? = null
) {
    val salaryInt: Int? = salary.toIntOrNull()

    val showActionButtons: Boolean = initial?.let { it != toFilterSettingsUi() } ?: run {
        address != null || industry != null || salaryInt != null || onlyWithoutSalary
    }
}

private fun FilterSettingsUiState.toFilterSettingsUi(): FilterSettingsUi = FilterSettingsUi(
    address = address,
    industry = industry,
    salary = salaryInt,
    onlyWithSalary = onlyWithoutSalary
)
