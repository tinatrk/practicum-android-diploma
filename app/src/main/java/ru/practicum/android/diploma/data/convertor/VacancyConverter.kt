package ru.practicum.android.diploma.data.convertor

import ru.practicum.android.diploma.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.domain.models.VacancyBrief
import ru.practicum.android.diploma.domain.models.VacancyPage

class VacancyConverter {

    fun map(vacancyResponse: VacancyResponse): VacancyPage {
        return VacancyPage(
            found = vacancyResponse.found,
            pages = vacancyResponse.pages,
            page = vacancyResponse.page,
            vacancies = vacancyResponse.vacancies.map { vacancyDto ->
                VacancyBrief(
                    id = vacancyDto.id,
                    name = vacancyDto.name,
                    city = vacancyDto.address?.city,
                    employerName = vacancyDto.employer?.name,
                    employerLogo = vacancyDto.employer?.logo,
                    salaryFrom = vacancyDto.salary?.from,
                    salaryTo = vacancyDto.salary?.to,
                    salaryCurrency = vacancyDto.salary?.currency
                )
            }
        )
    }
}
