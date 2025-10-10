package ru.practicum.android.diploma.domain.models.filters

data class FilterAddress(
    val country: FilterCountry,
    val region: FilterRegion?
)
