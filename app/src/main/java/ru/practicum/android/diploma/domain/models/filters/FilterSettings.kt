package ru.practicum.android.diploma.domain.models.filters

data class FilterSettings(
    val address: FilterAddress?,
    val industry: FilterIndustry?,
    val salary: Int?,
    val onlyWithSalary: Boolean?
)
