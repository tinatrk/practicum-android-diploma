package ru.practicum.android.diploma.domain.search.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource
import ru.practicum.android.diploma.domain.search.models.VacancyPage

interface VacancySearchRepository {
    fun search(
        query: String,
        options: Map<String, Int>,
        onlyWithSalary: Boolean
    ): Flow<Resource<VacancyPage, Failure>>
}
