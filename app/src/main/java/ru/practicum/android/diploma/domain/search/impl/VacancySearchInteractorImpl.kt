package ru.practicum.android.diploma.domain.search.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.search.models.VacancyPage
import ru.practicum.android.diploma.domain.search.repository.VacancySearchInteractor
import ru.practicum.android.diploma.domain.search.repository.VacancySearchRepository
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

class VacancySearchInteractorImpl(
    private val repository: VacancySearchRepository,
) : VacancySearchInteractor {
    override fun search(
        query: String,
        options: Map<String, Int>,
        onlyWithSalary: Boolean
    ): Flow<Resource<VacancyPage, Failure>> {
        return repository.search(
            query = query,
            options = options,
            onlyWithSalary = onlyWithSalary
        )
    }
}
