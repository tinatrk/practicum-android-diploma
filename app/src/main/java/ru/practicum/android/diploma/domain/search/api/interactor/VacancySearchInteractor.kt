package ru.practicum.android.diploma.domain.search.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.vacancy.VacancyPage
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

interface VacancySearchInteractor {
    fun search(
        query: String,
        options: Map<String, Int> = emptyMap(),
        onlyWithSalary: Boolean = false
    ): Flow<Resource<VacancyPage, Failure>>
}
