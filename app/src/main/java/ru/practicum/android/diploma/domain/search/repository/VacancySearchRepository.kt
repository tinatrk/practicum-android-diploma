package ru.practicum.android.diploma.domain.search.repository

import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource
import ru.practicum.android.diploma.domain.search.models.VacancyPage
import kotlinx.coroutines.flow.Flow

interface VacancySearchRepository {
    fun search(
        query: String,
        options: Map<String, Int>,
        onlyWithSalary: Boolean
    ): Flow<Resource<VacancyPage, Failure>>
}
