package ru.practicum.android.diploma.domain.details.models

data class FilterArea(
    val id: Int,
    val name: String?,
    val parentId: Int?,
    val areas: List<FilterArea>?
)
