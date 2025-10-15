package ru.practicum.android.diploma.presentation.converter

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.vacancy.Vacancy
import ru.practicum.android.diploma.domain.models.vacancy.VacancyBrief
import ru.practicum.android.diploma.presentation.models.VacancyBriefInfo
import ru.practicum.android.diploma.presentation.models.VacancyInfo
import ru.practicum.android.diploma.util.ResourceProvider

class VacancyConverter(
    private val resourceProvider: ResourceProvider
) {
    fun map(vacancy: VacancyBrief): VacancyBriefInfo {
        return VacancyBriefInfo(
            id = vacancy.id,
            name = vacancy.name ?: resourceProvider.getString(R.string.vacancy_name_unknown),
            city = vacancy.city ?: resourceProvider.getString(R.string.vacancy_city_unknown),
            employerName = vacancy.employerName ?: resourceProvider.getString(R.string.vacancy_employer_name_unknown),
            employerLogo = vacancy.employerLogo,
            salary = getSalaryString(vacancy.salaryFrom, vacancy.salaryTo, vacancy.salaryCurrency),
        )
    }

    fun map(vacancy: Vacancy): VacancyInfo {
        return VacancyInfo(
            id = vacancy.id,
            name = vacancy.name ?: resourceProvider.getString(R.string.vacancy_name_unknown),
            description = vacancy.description,
            salary = getSalaryString(vacancy.salary?.from, vacancy.salary?.to, vacancy.salary?.currency),
            address = vacancy.address?.fullAddress,
            experience = vacancy.experience ?: resourceProvider.getString(R.string.no_experience),
            schedule = vacancy.schedule,
            employment = vacancy.employment,
            contacts = vacancy.contacts,
            employerName = vacancy.employer?.name ?: resourceProvider.getString(R.string.vacancy_employer_name_unknown),
            employerLogo = vacancy.employer?.logo,
            area = vacancy.area?.name ?: resourceProvider.getString(R.string.vacancy_city_unknown),
            skills = vacancy.skills,
            url = vacancy.url,
            industry = vacancy.industry,
            isFavorite = vacancy.isFavorite
        )
    }

    fun map(vacancies: List<VacancyBrief>): List<VacancyBriefInfo> {
        return vacancies.map { map(it) }
    }

    private fun getSalaryString(from: Int?, to: Int?, currency: String?): String {
        if (currency == null) return resourceProvider.getString(R.string.salary_unknown)

        return when {
            from != null && to != null && from == to ->
                resourceProvider.getString(R.string.salary, getNumberStr(from), currency)

            from != null && to != null ->
                resourceProvider.getString(R.string.salary_from_to, getNumberStr(from), getNumberStr(to), currency)

            from != null ->
                resourceProvider.getString(R.string.salary_from, getNumberStr(from), currency)

            to != null ->
                resourceProvider.getString(R.string.salary_to, getNumberStr(to), currency)

            else ->
                resourceProvider.getString(R.string.salary_unknown)
        }
    }

    private fun getNumberStr(number: Int): String {
        return String.format(null, "%,d", number).replace(',', ' ')
    }
}
