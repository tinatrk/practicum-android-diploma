package ru.practicum.android.diploma.domain.models.filters

import kotlinx.serialization.Serializable

@Serializable
data class FilterRegion(
    val id: Int,
    val name: String
)
