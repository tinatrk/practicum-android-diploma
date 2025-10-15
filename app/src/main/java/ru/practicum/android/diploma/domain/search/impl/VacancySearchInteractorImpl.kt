package ru.practicum.android.diploma.domain.search.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.filters.FilterSettings
import ru.practicum.android.diploma.domain.models.vacancy.VacancyPage
import ru.practicum.android.diploma.domain.search.api.interactor.VacancySearchInteractor
import ru.practicum.android.diploma.domain.search.api.repository.VacancySearchRepository
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

class VacancySearchInteractorImpl(
    private val repository: VacancySearchRepository,
) : VacancySearchInteractor {
    override fun search(
        query: String,
        page: Int,
        filterSettings: FilterSettings?
    ): Flow<Resource<VacancyPage, Failure>> {
        return repository.search(
            query = query,
            page = page,
            filterSettings = filterSettings
        )
    }
}
