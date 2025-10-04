package ru.practicum.android.diploma.domain.details.api.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.util.common.Failure
import ru.practicum.android.diploma.util.common.Resource

interface VacancyDetailsInteractor {

    fun getVacancyDetails(vacancyId: String): Flow<Resource<VacancyDetail, Failure>>
}
