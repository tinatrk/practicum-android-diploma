package ru.practicum.android.diploma.presentation.filters.models

import ru.practicum.android.diploma.domain.models.filters.FilterAddress
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.domain.models.filters.FilterSettings

data class FilterSettingsState(
    val address: FilterAddress? = null,
    val industry: FilterIndustry? = null,
    val salary: String = "",
    val onlyWithoutSalary: Boolean = false,
    val initial: FilterSettings? = null
) {
    val salaryInt: Int? = salary.toIntOrNull()

    val showActionButtons: Boolean = initial?.let { it != toFilterSettings() } ?: run {
        address != null || industry != null || salaryInt != null || onlyWithoutSalary
    }
}

private fun FilterSettingsState.toFilterSettings(): FilterSettings = FilterSettings(
    address = address,
    industry = industry,
    salary = salaryInt,
    onlyWithSalary = onlyWithoutSalary
)
