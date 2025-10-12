package ru.practicum.android.diploma.domain.models.filters

import kotlinx.serialization.Serializable

//@Serializable
data class FilterAddress(
    val country: FilterCountry,
    val region: FilterRegion? = null
)
