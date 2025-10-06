package ru.practicum.android.diploma.presentation.converter

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyBrief
import ru.practicum.android.diploma.presentation.models.VacancyBriefInfo
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

    private fun getSalaryString(from: Int?, to: Int?, currency: String?): String {
        if (currency == null) return resourceProvider.getString(R.string.salary_unknown)

        return when {
            from != null && to != null -> {
                resourceProvider.getString(R.string.salary_from_to, getNumberStr(from), getNumberStr(to), currency)
            }

            from != null && to == null -> {
                resourceProvider.getString(R.string.salary_from, getNumberStr(from), currency)
            }

            from == null && to != null -> {
                resourceProvider.getString(R.string.salary_to, getNumberStr(to), currency)
            }

            else -> {
                resourceProvider.getString(R.string.salary_unknown)
            }
        }
    }

    private fun getNumberStr(number: Int): String {
        return String.format(null, "%,d", number).replace(',', ' ')
    }
}
