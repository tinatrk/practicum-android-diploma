package ru.practicum.android.diploma.domain.models.filters

import kotlinx.serialization.Serializable

//@Serializable
data class FilterSettings(
    val address: FilterAddress? = null,
    val industry: FilterIndustry? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean? = null
)
