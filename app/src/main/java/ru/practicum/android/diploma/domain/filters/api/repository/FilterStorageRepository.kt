package ru.practicum.android.diploma.domain.filters.api.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.filters.FilterSettings

interface FilterStorageRepository {
    fun getFilterSettings(): Flow<FilterSettings?>

    fun saveFilterSettings(filterSettings: FilterSettings)

    fun clearFilterSettings()
}
