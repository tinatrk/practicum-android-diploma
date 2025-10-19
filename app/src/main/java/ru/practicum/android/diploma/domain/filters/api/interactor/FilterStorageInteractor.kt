package ru.practicum.android.diploma.domain.filters.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.filters.FilterSettings

interface FilterStorageInteractor {
    fun getFilterSettings(): Flow<FilterSettings?>

    fun saveFilterSettings(filterSettings: FilterSettings)

    fun clearFilterSettings()
}
