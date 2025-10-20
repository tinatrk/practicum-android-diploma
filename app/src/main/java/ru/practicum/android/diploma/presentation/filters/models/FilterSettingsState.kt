package ru.practicum.android.diploma.presentation.filters.models

import androidx.compose.runtime.Immutable
import ru.practicum.android.diploma.domain.models.filters.FilterAddress
import ru.practicum.android.diploma.domain.models.filters.FilterIndustry
import ru.practicum.android.diploma.domain.models.filters.FilterSettings

@Immutable
data class FilterSettingsState(
    val address: FilterAddress? = null,
    val industry: FilterIndustry? = null,
    val salary: String = "",
    val onlyWithSalary: Boolean = false,
    val initial: FilterSettings? = null
) {
    val salaryInt: Int? = salary.toIntOrNull()

    val showActionButtons: Boolean = initial?.let { it != toFilterSettings() } ?: hasAnyFilter()
}

private fun FilterSettingsState.toFilterSettings(): FilterSettings = FilterSettings(
    address = address,
    industry = industry,
    salary = salaryInt,
    onlyWithSalary = onlyWithSalary
)

private fun FilterSettingsState.hasAnyFilter(): Boolean =
    address != null || industry != null || salaryInt != null || onlyWithSalary
