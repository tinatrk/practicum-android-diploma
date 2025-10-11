package ru.practicum.android.diploma.domain.models.filters

import kotlinx.serialization.Serializable

@Serializable
data class FilterSettings(
    val address: FilterAddress?,
    val industry: FilterIndustry?,
    val salary: Int?,
    val onlyWithSalary: Boolean?
)
