package ru.practicum.android.diploma.domain.filters.api.interactor

import ru.practicum.android.diploma.domain.models.filters.FilterSettings

interface FilterStorageInteractor {
    fun getFilterSettings(): FilterSettings?

    fun saveFilterSettings(filterSettings: FilterSettings)

    fun clearFilterSettings()
}
