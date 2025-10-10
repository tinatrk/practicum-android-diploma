package ru.practicum.android.diploma.domain.filters.impl

import ru.practicum.android.diploma.domain.filters.api.interactor.FilterStorageInteractor
import ru.practicum.android.diploma.domain.filters.api.repository.FilterStorageRepository
import ru.practicum.android.diploma.domain.models.filters.FilterSettings

class FilterStorageInteractorImpl(
    private val filterStorageRepository: FilterStorageRepository
) : FilterStorageInteractor {
    override fun saveFilterSettings(filterSettings: FilterSettings) {
        filterStorageRepository.saveFilterSettings(filterSettings = filterSettings)
    }

    override fun getFilterSettings(): FilterSettings? {
        return filterStorageRepository.getFilterSettings()
    }

    override fun clearFilterSettings() {
        filterStorageRepository.clearFilterSettings()
    }
}
