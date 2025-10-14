package ru.practicum.android.diploma.domain.search.api.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.filters.FilterSettings
import ru.practicum.android.diploma.domain.models.vacancy.VacancyPage
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

interface VacancySearchRepository {
    fun search(
        query: String,
        page: Int,
        filterSettings: FilterSettings? = null
    ): Flow<Resource<VacancyPage, Failure>>
}
