package ru.practicum.android.diploma.domain.filters.api.repository

import ru.practicum.android.diploma.domain.models.filters.FilterSettings

interface FilterStorageRepository {
    fun getFilterSettings(): FilterSettings?

    fun saveFilterSettings(filterSettings: FilterSettings)

    fun clearFilterSettings()
}
