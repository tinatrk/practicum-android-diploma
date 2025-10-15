package ru.practicum.android.diploma.domain.details.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.details.api.interactor.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.details.api.repository.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

class VacancyDetailsInteractorImpl(
    private val repository: VacancyDetailsRepository
) : VacancyDetailsInteractor {

    override fun getVacancyDetails(vacancyId: String): Flow<Resource<Vacancy, Failure>> {
        return repository.getVacancyDetails(vacancyId)
    }
}
